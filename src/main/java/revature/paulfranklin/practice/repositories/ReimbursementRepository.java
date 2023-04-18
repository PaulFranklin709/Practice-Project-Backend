package revature.paulfranklin.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revature.paulfranklin.practice.entities.Reimbursement;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, String> {
}
