package no.hvl.dat250.h600871.expass7.controller.data;

import no.hvl.dat250.h600871.expass7.model.Vote;
import no.hvl.dat250.h600871.expass7.model.VoteOption;

import java.time.Instant;
import java.util.Optional;

public record VoteDTO(
        Optional<Long> id,
        Optional<Instant> publishedAt,
        Optional<String> username,
        Optional<VoteOptionEntry> voteOption
) {


    public static VoteDTO voteOptions(Vote vote) {
        return new VoteDTO(
                Optional.of(vote.getId()),
                Optional.ofNullable(vote.getPublishedAt()),
                Optional.of(vote.getUser().getUsername()),
                Optional.of(new VoteOptionEntry(vote.getVoteOption())));
    }

    public record VoteOptionEntry(Optional<Long> id, Optional<String> caption) {
        VoteOptionEntry(VoteOption voteOption) {
            this(Optional.of(voteOption.getId()), Optional.of(voteOption.getCaption()));
        }
    }
}
