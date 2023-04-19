package revature.paulfranklin.practice.services;

import org.springframework.stereotype.Service;
import revature.paulfranklin.practice.entities.Friendship;
import revature.paulfranklin.practice.entities.User;
import revature.paulfranklin.practice.repositories.FriendshipRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;

    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public List<String> getFriendsByUserId(String userId) {
        List<Friendship> friendships = friendshipRepository.findAllByUserUserId(userId);

        List<String> friendNames = new LinkedList<>();
        friendships.forEach(friendship -> friendNames.add(friendship.getFriend().getUsername()));

        return friendNames;
    }

    public boolean hasFriend(User user, User friend) {
        List<Friendship> friendships = friendshipRepository.findAllByUserUserId(user.getUserId());

        boolean contains = false;
        for(Friendship friendship : friendships) {
            if (friend.getUserId().equals(friendship.getFriend().getUserId())) {
                contains = true;
            }
        }

        return contains;
    }

    public void createNewFriendship(User user, User friend) throws Exception {
        if (hasFriend(user, friend)) {
            throw new Exception("User already has friend");
        }
        if (user.getUserId().equals(friend.getUserId())) {
            throw new Exception("User cannot friend itself");
        }

        Friendship friendship = new Friendship(UUID.randomUUID().toString(), user, friend);
        friendshipRepository.save(friendship);
    }
}
