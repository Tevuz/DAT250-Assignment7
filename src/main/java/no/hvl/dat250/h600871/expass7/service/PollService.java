package no.hvl.dat250.h600871.expass7.service;

import no.hvl.dat250.h600871.expass7.model.Poll;
import no.hvl.dat250.h600871.expass7.repository.PollRepository;
import no.hvl.dat250.h600871.expass7.service.Exceptions.PollException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class PollService {

    private PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll createPoll(Poll poll) {
        if (pollRepository.existsById(poll.getId()))
            throw new PollException(String.format("Poll with id %s already exists", poll.getId()));
        return pollRepository.save(poll);
    }

    public Poll updatePoll(Poll poll) {
        if (!pollRepository.existsById(poll.getId()))
            throw new PollException(String.format("Poll with id %s does not exists", poll.getId()));

        return pollRepository.save(poll);
    }

    public Optional<Poll> readPollById(long id) {
        return pollRepository.findById(id);
    }

    public List<Poll> readAllPolls() {
        return StreamSupport.stream(pollRepository.findAll().spliterator(), false).toList();
    }

    public void deletePollById(long id) throws PollException {
        if (!pollRepository.existsById(id))
            throw new PollException("Poll not found");

        pollRepository.deleteById(id);
    }
}
