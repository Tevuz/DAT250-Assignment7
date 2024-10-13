package no.hvl.dat250.h600871.expass7.controller;

import no.hvl.dat250.h600871.expass7.controller.data.PollDTO;
import no.hvl.dat250.h600871.expass7.model.Poll;
import no.hvl.dat250.h600871.expass7.model.User;
import no.hvl.dat250.h600871.expass7.model.VoteOption;
import no.hvl.dat250.h600871.expass7.service.Exceptions.PollException;
import no.hvl.dat250.h600871.expass7.service.PollService;
import no.hvl.dat250.h600871.expass7.service.UserService;
import no.hvl.dat250.h600871.expass7.service.VoteOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/polls")
@CrossOrigin
public class PollController {

    private PollService pollService;
    private UserService userService;
    private VoteOptionService voteOptionService;

    public PollController(@Autowired PollService pollService, @Autowired UserService userService, @Autowired VoteOptionService voteOptionService) {
        this.pollService = pollService;
        this.userService = userService;
        this.voteOptionService = voteOptionService;
    }

    @PostMapping("")
    public ResponseEntity<PollDTO> createPoll(@RequestBody PollDTO pollInfo) {

        if (pollInfo.author_username().isEmpty())
            return ResponseEntity.badRequest().build();
        Optional<User> author_entry = userService.readUserByUsername(pollInfo.author_username().get());
        if (author_entry.isEmpty()) {
            User user = new User();
            user.setUsername(pollInfo.author_username().get());
            author_entry = Optional.of(userService.createUser(user));
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (pollInfo.voteOptions().isEmpty())
            return ResponseEntity.badRequest().build();

        final Poll poll = new Poll();
        pollInfo.question().ifPresent(poll::setQuestion);
        pollInfo.publishedAt().ifPresent(poll::setPublishedAt);
        pollInfo.validUntil().ifPresent(poll::setValidUntil);
        poll.setAuthor(author_entry.get());
        poll.setVoteOptions(pollInfo.voteOptions().get().stream().map(voteOptionInfo -> {
            VoteOption voteOption = new VoteOption();
            voteOption.setCaption(voteOptionInfo.caption());
            voteOption.setVotes(new ArrayList<>());
            voteOption.setPoll(poll);
            return voteOption;
        }).toList());

        Poll result = pollService.createPoll(poll);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(PollDTO.pollVotes(result));
    }

    @GetMapping("")
    public ResponseEntity<List<PollDTO>> getPolls() {
        return ResponseEntity.ok(pollService.readAllPolls().stream().map(PollDTO::pollVotes).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollDTO> getPollById(@PathVariable long id) {
        Optional<Poll> poll = pollService.readPollById(id);
        if (poll.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(PollDTO.pollVotes(poll.get()));
    }

    @GetMapping("/{id}/votes")
    public ResponseEntity<PollDTO> getPollVotes(@PathVariable long id) {
        Optional<Poll> poll = pollService.readPollById(id);
        if (poll.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(PollDTO.pollVotes(poll.get()));
    }

    @PutMapping("")
    public ResponseEntity<PollDTO> updatePoll(@RequestBody Poll poll) {
        return ResponseEntity.ok(PollDTO.poll(pollService.updatePoll(poll)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePoll(@PathVariable long id) {
        try {
            pollService.deletePollById(id);
            return ResponseEntity.noContent().build();
        } catch (PollException exception) {
            return ResponseEntity.notFound().build();
        }
    }


}
