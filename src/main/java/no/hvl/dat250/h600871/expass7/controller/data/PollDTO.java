package no.hvl.dat250.h600871.expass7.controller.data;

import no.hvl.dat250.h600871.expass7.model.Poll;
import no.hvl.dat250.h600871.expass7.model.User;
import no.hvl.dat250.h600871.expass7.model.Vote;
import no.hvl.dat250.h600871.expass7.model.VoteOption;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public record PollDTO(
        Optional<Long> id,
        Optional<String> question,
        Optional<Instant> publishedAt,
        Optional<Instant> validUntil,
        Optional<String> author_username,
        Optional<List<PollVoteOptionEntries>> voteOptions
) {

    public static PollDTO poll(Poll poll) {
        return new PollDTO(Optional.of(poll.getId()),
                Optional.ofNullable(poll.getQuestion()),
                Optional.ofNullable(poll.getPublishedAt()),
                Optional.ofNullable(poll.getValidUntil()),
                Optional.ofNullable(poll.getAuthor().getUsername()),
                Optional.empty());
    }

    public static PollDTO pollVotes(Poll poll) {
        return new PollDTO(Optional.of(poll.getId()),
                Optional.ofNullable(poll.getQuestion()),
                Optional.ofNullable(poll.getPublishedAt()),
                Optional.ofNullable(poll.getValidUntil()),
                Optional.ofNullable(poll.getAuthor().getUsername()),
                Optional.of(poll.getVoteOptions().stream().map(PollVoteOptionEntries::new).toList()));
    }

    public record PollVoteOptionEntries(long id, String caption, List<String> votes) {
        public PollVoteOptionEntries(VoteOption voteOption) {
            this(voteOption.getId(), voteOption.getCaption(), voteOption.getVotes().stream().map(Vote::getUser).map(User::getUsername).toList());
        }
    }

}
