package revature.paulfranklin.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revature.paulfranklin.practice.entities.Friendship;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, String> {
    List<Friendship> findAllByUserUserId(String userId);
}
