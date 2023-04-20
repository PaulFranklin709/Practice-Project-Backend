package revature.paulfranklin.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revature.paulfranklin.practice.entities.Reimbursement;

import java.util.List;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, String> {
    List<Reimbursement> findAllByAuthorUserId(String authorId);
}
