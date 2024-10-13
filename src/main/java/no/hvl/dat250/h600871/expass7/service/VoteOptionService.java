package no.hvl.dat250.h600871.expass7.service;

import no.hvl.dat250.h600871.expass7.model.VoteOption;
import no.hvl.dat250.h600871.expass7.repository.VoteOptionRepository;
import no.hvl.dat250.h600871.expass7.service.Exceptions.VoteOptionException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class VoteOptionService {

    private VoteOptionRepository voteOptionRepository;

    public VoteOptionService(VoteOptionRepository voteOptionRepository) {
        this.voteOptionRepository = voteOptionRepository;
    }

    public VoteOption createVoteOption(VoteOption voteOption) {
        if (voteOptionRepository.existsById(voteOption.getId()))
            throw new VoteOptionException(String.format("VoteOption with %s id already exists", voteOption.getId()));

        return voteOptionRepository.save(voteOption);
    }

    public VoteOption updateVoteOption(VoteOption voteOption) {
        if (!voteOptionRepository.existsById(voteOption.getId()))
            throw new VoteOptionException(String.format("VoteOption with %s id does not exist", voteOption.getId()));

        return voteOptionRepository.save(voteOption);
    }

    public Optional<VoteOption> readVoteOptionById(long id) {
        if (!voteOptionRepository.existsById(id))
            throw new VoteOptionException(String.format("VoteOption with %s id does not exist", id));

        return voteOptionRepository.findById(id);
    }

    public List<VoteOption> readAllVoteOptions() {
        return StreamSupport.stream(voteOptionRepository.findAll().spliterator(), false).toList();
    }

    public void deleteVoteOptionById(long id) {
        voteOptionRepository.deleteById(id);
    }
}
