package revature.paulfranklin.practice.dtos.requests;

public class NewReimbursementRequest {
    private double amount;
    private String description;

    public NewReimbursementRequest() {
        super();
    }

    public NewReimbursementRequest(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
