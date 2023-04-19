package revature.paulfranklin.practice.dtos.requests;

public class DeleteFriendRequest {
    private String friendName;

    public DeleteFriendRequest() {
        super();
    }

    public DeleteFriendRequest(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
