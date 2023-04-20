package revature.paulfranklin.practice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import revature.paulfranklin.practice.dtos.requests.NewReimbursementRequest;
import revature.paulfranklin.practice.dtos.requests.ResolveReimbursementRequest;
import revature.paulfranklin.practice.dtos.responses.Principal;
import revature.paulfranklin.practice.dtos.responses.ReimbursementResponse;
import revature.paulfranklin.practice.entities.User;
import revature.paulfranklin.practice.exceptions.InvalidAuthException;
import revature.paulfranklin.practice.exceptions.InvalidReimbursementException;
import revature.paulfranklin.practice.exceptions.InvalidRequestException;
import revature.paulfranklin.practice.services.ReimbursementService;
import revature.paulfranklin.practice.services.TokenService;
import revature.paulfranklin.practice.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/reimbursements")
public class ReimbursementController {
    private final Logger logger = LoggerFactory.getLogger(ReimbursementController.class);
    private final ReimbursementService reimbursementService;
    private final TokenService tokenService;
    private final UserService userService;

    public ReimbursementController(ReimbursementService reimbursementService, TokenService tokenService, UserService userService) {
        this.reimbursementService = reimbursementService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ReimbursementResponse> getReimbursements(HttpServletRequest servReq) {
        String token = servReq.getHeader("authorization");
        if (token == null || token.isEmpty()) {
            throw new InvalidRequestException("Missing token");
        }

        Principal principal = tokenService.retrievePrincipalFromToken(token);

        try {
            Optional<User> userOptional = userService.getUserByUsername(principal.getUsername());
            userOptional.orElseGet(InvalidAuthException::userNotFound);
        } catch (InvalidAuthException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            return reimbursementService.getReimbursementsByAuthorId(principal.getUserId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newReimbursement(@RequestBody NewReimbursementRequest req, HttpServletRequest servReq) {
        String token = servReq.getHeader("authorization");
        if (token == null || token.isEmpty()) {
            throw new InvalidRequestException("Missing token");
        }

        Principal principal = tokenService.retrievePrincipalFromToken(token);

        User user;
        try {
            Optional<User> userOptional = userService.getUserByUsername(principal.getUsername());
            user = userOptional.orElseGet(InvalidAuthException::userNotFound);
        } catch (InvalidAuthException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            reimbursementService.createNewReimbursement(req, user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("id")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ReimbursementResponse getReimbursement(@RequestParam(name = "reimbId") String reimbId, HttpServletRequest servReq) {
        String token = servReq.getHeader("authorization");
        if (token == null || token.isEmpty()) {
            throw new InvalidRequestException("Missing token");
        }

        Principal principal = tokenService.retrievePrincipalFromToken(token);

        try {
            Optional<User> userOptional = userService.getUserByUsername(principal.getUsername());
            userOptional.orElseGet(InvalidAuthException::userNotFound);
        } catch (InvalidAuthException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            return reimbursementService.getReimbursementById(reimbId);
        } catch (InvalidReimbursementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void resolveReimbursement(@RequestBody ResolveReimbursementRequest req, HttpServletRequest servReq) {
        String token = servReq.getHeader("authorization");
        if (token == null || token.isEmpty()) {
            throw new InvalidRequestException("Missing token");
        }

        Principal principal = tokenService.retrievePrincipalFromToken(token);

        try {
            Optional<User> userOptional = userService.getUserByUsername(principal.getUsername());
            userOptional.orElseGet(InvalidAuthException::userNotFound);
        } catch (InvalidAuthException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            reimbursementService.updateReimbursementResolvedById(req.getReimbId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidReimbursementException.class)
    public InvalidReimbursementException handledReimbursementException (InvalidReimbursementException e) {
        logger.error(e.getMessage());
        return e;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    public InvalidRequestException handledRequestException (InvalidRequestException e) {
        logger.error(e.getMessage());
        return e;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidAuthException.class)
    public InvalidAuthException handledAuthException (InvalidAuthException e) {
        logger.warn(e.getMessage());
        return e;
    }
}
