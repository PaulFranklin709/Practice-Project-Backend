package revature.paulfranklin.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revature.paulfranklin.practice.entities.Friendship;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, String> {
}
