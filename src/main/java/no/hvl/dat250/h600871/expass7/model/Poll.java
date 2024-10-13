package no.hvl.dat250.h600871.expass7.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "poll")
public final class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    private User author;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poll")
    private List<VoteOption> voteOptions;

    public Poll() {
    }

    public Poll(
            long id,
            String question,
            Instant publishedAt,
            Instant validUntil,
            User author,
            List<VoteOption> voteOptions
    ) {
        this.id = id;
        this.question = question;
        this.publishedAt = publishedAt;
        this.validUntil = validUntil;
        this.author = author;
        this.voteOptions = voteOptions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<VoteOption> getVoteOptions() {
        return voteOptions;
    }

    public void setVoteOptions(List<VoteOption> voteOptions) {
        this.voteOptions = voteOptions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Poll) obj;
        return this.id == that.id &&
                Objects.equals(this.question, that.question) &&
                Objects.equals(this.publishedAt, that.publishedAt) &&
                Objects.equals(this.validUntil, that.validUntil) &&
                Objects.equals(this.author, that.author) &&
                Objects.equals(this.voteOptions, that.voteOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, publishedAt, validUntil, author, voteOptions);
    }

    @Override
    public String toString() {
        return "Poll[" +
                "id=" + id + ", " +
                "question=" + question + ", " +
                "publishedAt=" + publishedAt + ", " +
                "validUntil=" + validUntil + ", " +
                "author=" + author + ", " +
                "voteOptions=" + voteOptions + ']';
    }
}
