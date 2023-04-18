package revature.paulfranklin.practice.services;

import org.springframework.stereotype.Service;
import revature.paulfranklin.practice.entities.Friendship;
import revature.paulfranklin.practice.repositories.FriendshipRepository;

import java.util.LinkedList;
import java.util.List;

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
}
