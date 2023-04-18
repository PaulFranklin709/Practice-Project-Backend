package revature.paulfranklin.practice.entities;

import javax.persistence.*;

@Entity
@Table
public class Friendship {
    @Id
    private String friendshipId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User friend;

    public Friendship() {
    }

    public Friendship(String friendshipId, User user, User friend) {
        this.friendshipId = friendshipId;
        this.user = user;
        this.friend = friend;
    }

    public String getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(String friendshipId) {
        this.friendshipId = friendshipId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
