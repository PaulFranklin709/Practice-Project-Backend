package revature.paulfranklin.practice.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
public class Reimbursement {
    @Id
    private String reimbId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Timestamp submitted;

    @Column
    private Timestamp resolved;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User author;

    public Reimbursement() {
    }

    public Reimbursement(String reimbId, Double amount, Timestamp submitted, Timestamp resolved, String description, User author) {
        this.reimbId = reimbId;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.author = author;
    }

    public String getReimbId() {
        return reimbId;
    }

    public void setReimbId(String reimbId) {
        this.reimbId = reimbId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
