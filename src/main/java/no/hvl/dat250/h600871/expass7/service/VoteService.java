package no.hvl.dat250.h600871.expass7.service;

import no.hvl.dat250.h600871.expass7.model.Vote;
import no.hvl.dat250.h600871.expass7.repository.VoteRepository;
import no.hvl.dat250.h600871.expass7.service.Exceptions.VoteException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class VoteService {

    private VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote createVote(Vote vote) {
        if (voteRepository.existsById(vote.getId()))
            throw new VoteException(String.format("Vote with id %s already exists", vote.getId()));

        return voteRepository.save(vote);
    }

    public Vote updateVote(Vote vote) {
        if (!voteRepository.existsById(vote.getId()))
            throw new VoteException(String.format("Vote with id %s does not exist", vote.getId()));

        return voteRepository.save(vote);
    }

    public Optional<Vote> readVoteById(long id) {
        if (!voteRepository.existsById(id))
            throw new VoteException(String.format("Vote with id %s does not exist", id));

        return voteRepository.findById(id);
    }

    public List<Vote> readAllVotes() {
        return StreamSupport.stream(voteRepository.findAll().spliterator(), false).toList();
    }

    public void deleteVoteById(long id) {
        if (!voteRepository.existsById(id))
            throw new VoteException(String.format("Vote with id %s does not exist", id));

        voteRepository.deleteById(id);
    }
}
