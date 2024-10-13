package no.hvl.dat250.h600871.expass7.repository;

import no.hvl.dat250.h600871.expass7.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsUserByUsername(String username);

    Optional<User> findUserByUsername(String username);

    void deleteUserByUsername(String username);
}
