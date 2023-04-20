package revature.paulfranklin.practice.services;

import org.springframework.stereotype.Service;
import revature.paulfranklin.practice.entities.Reimbursement;
import revature.paulfranklin.practice.repositories.ReimbursementRepository;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReimbursementService {
    private final ReimbursementRepository reimbursementRepository;

    public ReimbursementService(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    public List<Reimbursement> getReimbursementsByAuthorId(String authorId) throws SQLException {
        List<Reimbursement> reimbursements;
        try {
            reimbursements = reimbursementRepository.findAllByAuthorUserId(authorId);
        } catch (Exception e) {
            throw new SQLException(e);
        }

        return reimbursements;
    }
}
