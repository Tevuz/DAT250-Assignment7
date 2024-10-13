package no.hvl.dat250.h600871.expass7.controller.data;

import no.hvl.dat250.h600871.expass7.model.VoteOption;

import java.util.Optional;

public record VoteOptionDTO(
        Optional<Long> id,
        Optional<String> caption,
        Optional<Integer> presentationOrder,
        Optional<Long> votes
) {

    public static VoteOptionDTO from(VoteOption voteOption) {
        return new VoteOptionDTO(
                Optional.of(voteOption.getId()),
                Optional.of(voteOption.getCaption()),
                Optional.of(voteOption.getPresentationOrder()),
                Optional.of(voteOption.getVotes().stream().count()));
    }

    public VoteOption into() {
        VoteOption option = new VoteOption();
        id.ifPresent(option::setId);
        caption.ifPresent(option::setCaption);
        presentationOrder.ifPresent(option::setPresentationOrder);
        return option;
    }
}
