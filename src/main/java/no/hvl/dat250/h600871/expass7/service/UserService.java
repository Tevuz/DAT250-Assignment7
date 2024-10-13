package no.hvl.dat250.h600871.expass7.service;

import no.hvl.dat250.h600871.expass7.model.User;
import no.hvl.dat250.h600871.expass7.repository.UserRepository;
import no.hvl.dat250.h600871.expass7.service.Exceptions.UserException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (userRepository.existsUserByUsername(user.getUsername()))
            throw new UserException(String.format("User named %s already exists", user.getUsername()));

        return userRepository.save(user);
    }

    public Optional<User> readUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<User> readAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).toList();
    }

    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId()))
            throw new UserException(String.format("User id: %s does not exist", user.getId()));

        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        if (!userRepository.existsById(user.getId()))
            throw new UserException(String.format("User id: %s does not exist", user.getId()));

        userRepository.delete(user);
    }


    public void deleteUserByUsername(String username) {
        if (userRepository.existsUserByUsername(username))
            throw new UserException("User not found");

        userRepository.deleteUserByUsername(username);
    }

    public Optional<User> readUserById(Long id) {
        return userRepository.findById(id);
    }
}
