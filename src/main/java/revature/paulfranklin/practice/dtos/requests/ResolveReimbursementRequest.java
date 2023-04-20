package revature.paulfranklin.practice.dtos.requests;

public class ResolveReimbursementRequest {
    private String reimbId;

    public ResolveReimbursementRequest() {
        super();
    }

    public ResolveReimbursementRequest(String reimbId) {
        this.reimbId = reimbId;
    }

    public String getReimbId() {
        return reimbId;
    }

    public void setReimbId(String reimbId) {
        this.reimbId = reimbId;
    }
}
