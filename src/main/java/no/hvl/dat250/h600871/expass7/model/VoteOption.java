package no.hvl.dat250.h600871.expass7.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "voteOption")
public final class VoteOption {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String caption;
    private int presentationOrder;
    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voteOption")
    private List<Vote> votes;

    public VoteOption(
            long id,
            String caption,
            int presentationOrder,
            Poll poll,
            List<Vote> votes
    ) {
        this.id = id;
        this.caption = caption;
        this.presentationOrder = presentationOrder;
        this.poll = poll;
        this.votes = votes;
    }

    public VoteOption() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getPresentationOrder() {
        return presentationOrder;
    }

    public void setPresentationOrder(int presentationOrder) {
        this.presentationOrder = presentationOrder;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
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
        var that = (VoteOption) obj;
        return this.id == that.id &&
                Objects.equals(this.caption, that.caption) &&
                this.presentationOrder == that.presentationOrder &&
                Objects.equals(this.poll, that.poll) &&
                Objects.equals(this.votes, that.votes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caption, presentationOrder, poll, votes);
    }

    @Override
    public String toString() {
        return "VoteOption[" +
                "id=" + id + ", " +
                "caption=" + caption + ", " +
                "presentationOrder=" + presentationOrder + ", " +
                "poll=" + poll + ", " +
                "votes=" + votes + ']';
    }
}
