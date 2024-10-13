package no.hvl.dat250.h600871.expass7.controller;

import no.hvl.dat250.h600871.expass7.controller.data.VoteDTO;
import no.hvl.dat250.h600871.expass7.model.User;
import no.hvl.dat250.h600871.expass7.model.Vote;
import no.hvl.dat250.h600871.expass7.model.VoteOption;
import no.hvl.dat250.h600871.expass7.service.Exceptions.VoteException;
import no.hvl.dat250.h600871.expass7.service.UserService;
import no.hvl.dat250.h600871.expass7.service.VoteOptionService;
import no.hvl.dat250.h600871.expass7.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/votes")
@CrossOrigin
public class VoteController {

    private VoteService voteService;
    private UserService userService;
    private VoteOptionService optionService;

    public VoteController(@Autowired VoteService service, @Autowired UserService userService, @Autowired VoteOptionService optionService) {
        this.voteService = service;
        this.userService = userService;
        this.optionService = optionService;
    }

    @PostMapping("")
    public ResponseEntity<VoteDTO> createVote(@RequestBody VoteDTO voteInfo) {
        if (voteInfo.username().isEmpty() || voteInfo.voteOption().isEmpty() || voteInfo.voteOption().get().id().isEmpty())
            return ResponseEntity.badRequest().build();

        Optional<User> user_entry = userService.readUserByUsername(voteInfo.username().get());
        if (user_entry.isEmpty()) {
            User user = new User();
            user.setUsername(voteInfo.username().get());
            user_entry = Optional.of(userService.createUser(user));
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<VoteOption> voteOption = optionService.readVoteOptionById(voteInfo.voteOption().get().id().get());

        Vote vote = new Vote();
        voteInfo.publishedAt().ifPresent(vote::setPublishedAt);
        vote.setUser(user_entry.get());
        vote.setVoteOption(voteOption.get());

        Vote result = voteService.createVote(vote);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(VoteDTO.voteOptions(result));
    }

    @GetMapping("")
    public ResponseEntity<List<VoteDTO>> getVotes() {
        return ResponseEntity.ok(voteService.readAllVotes().stream().map(VoteDTO::voteOptions).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteDTO> getVoteById(@PathVariable long id) {
        Optional<Vote> vote = voteService.readVoteById(id);
        if (vote.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(VoteDTO.voteOptions(vote.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoteDTO> updateVote(@PathVariable long id, @RequestBody VoteDTO voteInfo) {
        Optional<Vote> vote_entry = voteService.readVoteById(id);
        if (vote_entry.isEmpty())
            return ResponseEntity.notFound().build();

        Vote vote = vote_entry.get();
        voteInfo.publishedAt().ifPresent(vote::setPublishedAt);

        if (voteInfo.username().isPresent()) {
            Optional<User> user = userService.readUserByUsername(voteInfo.username().get());
            if (user.isEmpty())
                return ResponseEntity.notFound().build();
            vote.setUser(user.get());
        }

        Optional<VoteOption> voteOption = voteInfo.voteOption().flatMap(e -> e.id().flatMap(optionService::readVoteOptionById));
        if (voteOption.isEmpty())
            return ResponseEntity.notFound().build();

       return ResponseEntity.ok(VoteDTO.voteOptions(voteService.updateVote(vote)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVote(@PathVariable long id) {
        try {
            voteService.deleteVoteById(id);
            return ResponseEntity.noContent().build();
        } catch (VoteException exception) {
            return ResponseEntity.notFound().build();
        }
    }


}
