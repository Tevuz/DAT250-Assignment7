package no.hvl.dat250.h600871.expass7.controller.data;

import no.hvl.dat250.h600871.expass7.model.Poll;
import no.hvl.dat250.h600871.expass7.model.User;
import no.hvl.dat250.h600871.expass7.model.Vote;
import no.hvl.dat250.h600871.expass7.model.VoteOption;

import java.util.List;
import java.util.Optional;

public record UserDTO(Optional<Long> id, Optional<String> username, Optional<String> email, Optional<List<UserPollsEntries>> polls, Optional<List<UserVoteEntries>> votes) {

    public static UserDTO user(User user) {
        return new UserDTO(Optional.of(user.getId()),
                Optional.ofNullable(user.getUsername()),
                Optional.ofNullable(user.getEmail()),
                Optional.empty(),
                Optional.empty());
    }

    public static UserDTO userPolls(User user) {
        return new UserDTO(Optional.of(user.getId()),
                Optional.ofNullable(user.getUsername()),
                Optional.ofNullable(user.getEmail()),
                Optional.of(user.getPolls().stream().map(UserPollsEntries::new).toList()),
                Optional.empty());
    }

    public static UserDTO userVotes(User user) {
        return new UserDTO(Optional.of(user.getId()),
                Optional.ofNullable(user.getUsername()),
                Optional.ofNullable(user.getEmail()),
                Optional.empty(),
                Optional.of(user.getVotes().stream().map(UserVoteEntries::new).toList()));
    }

    public User into() {
        User user = new User();
        id.ifPresent(user::setId);
        username.ifPresent(user::setUsername);
        email.ifPresent(user::setEmail);
        return user;
    }

    public static record UserPollsEntries(long id, String question, List<String> voteOptions) {
        UserPollsEntries(Poll poll) {
            this(poll.getId(), poll.getQuestion(), poll.getVoteOptions().stream().map(VoteOption::getCaption).toList());
        }
    }

    public static record UserVoteEntries(long id, String question, String voteOptions) {
        UserVoteEntries(Vote vote) {
            this(vote.getId(), vote.getVoteOption().getPoll().getQuestion(), vote.getVoteOption().getCaption());
        }
    }

}
