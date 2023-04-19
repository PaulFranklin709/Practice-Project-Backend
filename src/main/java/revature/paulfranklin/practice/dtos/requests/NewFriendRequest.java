package revature.paulfranklin.practice.dtos.requests;

public class NewFriendRequest {
    private String friendName;

    public NewFriendRequest() {
        super();
    }

    public NewFriendRequest(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
