package no.hvl.dat250.h600871.expass7.controller;

import no.hvl.dat250.h600871.expass7.controller.data.UserDTO;
import no.hvl.dat250.h600871.expass7.model.User;
import no.hvl.dat250.h600871.expass7.service.Exceptions.UserException;
import no.hvl.dat250.h600871.expass7.service.Exceptions.VoteException;
import no.hvl.dat250.h600871.expass7.service.PollService;
import no.hvl.dat250.h600871.expass7.service.UserService;
import no.hvl.dat250.h600871.expass7.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private UserService userService;
    private VoteService voteService;
    private PollService pollService;

    public UserController(@Autowired UserService userService, @Autowired VoteService voteService, @Autowired PollService pollService) {
        this.userService = userService;
        this.voteService = voteService;
        this.pollService = pollService;
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userInfo) {
        try {
            User user = userService.createUser(userInfo.into());

            // TODO: commit user polls to repository
            // TODO: commit user votes to repository

            return ResponseEntity.status(HttpStatus.CREATED).header("/" + user.getUsername()).body(UserDTO.user(user));
        } catch (UserException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("username") String username) {
        Optional<User> user = userService.readUserByUsername(username);
        if (!user.isPresent())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(UserDTO.user(user.get()));
    }

    @GetMapping("/{username}/polls")
    public ResponseEntity<UserDTO> getUserPolls(@PathVariable("username") String username) {
        Optional<User> user = userService.readUserByUsername(username);
        if (!user.isPresent())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(UserDTO.userPolls(user.get()));
    }

    @GetMapping("/{username}/votes")
    public ResponseEntity<UserDTO> getUserVotes(@PathVariable("username") String username) {
        Optional<User> user = userService.readUserByUsername(username);
        if (!user.isPresent())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(UserDTO.userVotes(user.get()));
    }

    @GetMapping("")
    public ResponseEntity<Iterable<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.readAllUsers().stream().map(UserDTO::user).toList());
    }

    @PutMapping("")
    public ResponseEntity<UserDTO> updateVote(@RequestBody UserDTO userInfo) {
        if (userInfo.id().isEmpty())
            return ResponseEntity.badRequest().build();

        Optional<User> entry = userService.readUserById(userInfo.id().get());
        if (entry.isEmpty())
            return ResponseEntity.notFound().build();

        User user = entry.get();

        userInfo.username().ifPresent(user::setUsername);
        userInfo.email().ifPresent(user::setEmail);

        // TODO: commit user polls to repository
        //userInfo.polls().ifPresent(polls -> {
        //    user.getPolls().clear();
        //    polls.stream().map(p -> p.id()).map(pollService::readPollById).filter(Optional::isPresent).map(Optional::get).forEach(user.getPolls()::add);
        //});

        // TODO: commit user votes to repository
        //userInfo.votes().ifPresent(votes -> {
        //    user.getVotes().clear();
        //    votes.stream().map(v -> v.id()).map(voteService::readVoteById).filter(Optional::isPresent).map(Optional::get).forEach(user.getVotes()::add);
        //});

        return ResponseEntity.ok(UserDTO.user(userService.updateUser(user)));
    }

    @DeleteMapping("{username}")
    public ResponseEntity deleteUser(@PathVariable String username) {
        try {
            userService.deleteUserByUsername(username);
            return ResponseEntity.noContent().build();
        } catch (VoteException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
