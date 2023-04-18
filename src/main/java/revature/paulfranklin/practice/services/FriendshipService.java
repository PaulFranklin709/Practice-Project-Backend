package revature.paulfranklin.practice.services;

import org.springframework.stereotype.Service;
import revature.paulfranklin.practice.entities.Friendship;
import revature.paulfranklin.practice.repositories.FriendshipRepository;

import java.util.List;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;

    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public List<Friendship> getFriendsByUserId(String userId) {
        return friendshipRepository.findAllByUserUserId(userId);
    }
}
