package no.hvl.dat250.h600871.expass7.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "app_user")
public final class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String username;
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<Poll> polls;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Vote> votes;

    public User(
            long id,
            String username,
            String email,
            List<Poll> polls,
            List<Vote> votes
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.polls = polls;
        this.votes = votes;
    }

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Poll> getPolls() {
        return polls;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (User) obj;
        return this.id == that.id &&
                Objects.equals(this.username, that.username) &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.polls, that.polls) &&
                Objects.equals(this.votes, that.votes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, polls, votes);
    }

    @Override
    public String toString() {
        return "User[" +
                "id=" + id + ", " +
                "username=" + username + ", " +
                "email=" + email + ", " +
                "polls=" + polls + ", " +
                "votes=" + votes + ']';
    }

}
