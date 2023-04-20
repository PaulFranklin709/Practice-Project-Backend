package revature.paulfranklin.practice.dtos.responses;

import revature.paulfranklin.practice.entities.Reimbursement;

import java.sql.Timestamp;

public class ReimbursementResponse {
    private String reimbId;
    private double amount;
    private Timestamp submitted;
    private Timestamp resolved;
    private String description;
    private String author;

    public ReimbursementResponse() {
        super();
    }

    public ReimbursementResponse(String reimbId, double amount, Timestamp submitted, Timestamp resolved, String description, String author) {
        this.reimbId = reimbId;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.author = author;
    }

    public ReimbursementResponse(Reimbursement reimbursement) {
        this.reimbId = reimbursement.getReimbId();
        this.amount = reimbursement.getAmount();
        this.submitted = reimbursement.getSubmitted();
        this.resolved = reimbursement.getResolved();
        this.description = reimbursement.getDescription();
        this.author = reimbursement.getAuthor().getUsername();
    }

    public String getReimbId() {
        return reimbId;
    }

    public void setReimbId(String reimbId) {
        this.reimbId = reimbId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
