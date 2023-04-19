package revature.paulfranklin.practice.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import revature.paulfranklin.practice.entities.Friendship;
import revature.paulfranklin.practice.entities.User;
import revature.paulfranklin.practice.repositories.FriendshipRepository;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;

    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public List<Friendship> getFriendshipsByUserId(String userId) throws SQLException {
        List<Friendship> friendships;
        try {
            friendships = friendshipRepository.findAllByUserUserId(userId);
        } catch (Exception e) {
            throw new SQLException(e);
        }

        return friendships;
    }

    public List<String> getFriendNamesByUserId(String userId) throws SQLException {
        List<Friendship> friendships = getFriendshipsByUserId(userId);

        List<String> friendNames = new LinkedList<>();
        friendships.forEach(friendship -> friendNames.add(friendship.getFriend().getUsername()));

        return friendNames;
    }

    public boolean hasFriend(User user, User friend) throws SQLException {
        List<Friendship> friendships;
        try {
            friendships = friendshipRepository.findAllByUserUserId(user.getUserId());
        } catch (Exception e) {
            throw new SQLException(e);
        }

        boolean contains = false;
        for(Friendship friendship : friendships) {
            if (friend.getUserId().equals(friendship.getFriend().getUserId())) {
                contains = true;
            }
        }

        return contains;
    }

    public void createNewFriendship(User user, User friend) throws SQLException {
        if (hasFriend(user, friend)) {
            throw new IllegalArgumentException("User already has friend");
        }
        if (user.getUserId().equals(friend.getUserId())) {
            throw new IllegalArgumentException("User cannot friend itself");
        }

        Friendship friendship = new Friendship(UUID.randomUUID().toString(), user, friend);
        try {
            friendshipRepository.save(friendship);
        } catch (DataIntegrityViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public void deleteFriendship(String userId, String friendName) throws SQLException {
        List<Friendship> friendships = getFriendshipsByUserId(userId);

        String friendshipId = null;
        for(Friendship friendship : friendships) {
            if (friendship.getFriend().getUsername().equals(friendName)) {
                friendshipId = friendship.getFriendshipId();
            }
        }

        if (friendshipId != null) {
            try {
                friendshipRepository.deleteById(friendshipId);
            } catch (Exception e) {
                throw new SQLException();
            }
        }
    }
}
