package revature.paulfranklin.practice.services;

import org.springframework.stereotype.Service;
import revature.paulfranklin.practice.dtos.requests.NewReimbursementRequest;
import revature.paulfranklin.practice.dtos.responses.ReimbursementResponse;
import revature.paulfranklin.practice.entities.Reimbursement;
import revature.paulfranklin.practice.entities.User;
import revature.paulfranklin.practice.repositories.ReimbursementRepository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class ReimbursementService {
    private final ReimbursementRepository reimbursementRepository;

    public ReimbursementService(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    public List<ReimbursementResponse> getReimbursementsByAuthorId(String authorId) throws SQLException {
        List<Reimbursement> reimbursements;
        try {
            reimbursements = reimbursementRepository.findAllByAuthorUserId(authorId);
        } catch (Exception e) {
            throw new SQLException(e);
        }

        List<ReimbursementResponse> reimbursementResponses = new LinkedList<>();

        reimbursements.forEach(reimbursement -> reimbursementResponses.add(new ReimbursementResponse(reimbursement)));

        return reimbursementResponses;
    }

    public void createNewReimbursement(NewReimbursementRequest req, User user) throws SQLException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Reimbursement reimbursement = new Reimbursement(UUID.randomUUID().toString(), req.getAmount(), timestamp, null, req.getDescription(), user);
        try {
            reimbursementRepository.save(reimbursement);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
}
