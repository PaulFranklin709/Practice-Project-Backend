package revature.paulfranklin.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import revature.paulfranklin.practice.entities.Reimbursement;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, String> {
    List<Reimbursement> findAllByAuthorUserId(String authorId);

    @Modifying
    @Query(value = "UPDATE reimbursement " +
            "SET resolved = ?2 " +
            "WHERE reimb_id = ?1", nativeQuery = true)
    void updateResolvedById(String reimbId, Timestamp resolved);
}
