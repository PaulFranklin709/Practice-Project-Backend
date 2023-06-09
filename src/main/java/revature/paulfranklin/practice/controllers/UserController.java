package revature.paulfranklin.practice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import revature.paulfranklin.practice.dtos.requests.NewUserRequest;
import revature.paulfranklin.practice.dtos.responses.Principal;
import revature.paulfranklin.practice.entities.User;
import revature.paulfranklin.practice.exceptions.InvalidAuthException;
import revature.paulfranklin.practice.exceptions.InvalidRequestException;
import revature.paulfranklin.practice.exceptions.InvalidUserException;
import revature.paulfranklin.practice.services.TokenService;
import revature.paulfranklin.practice.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Principal signup(@RequestBody NewUserRequest req) {
        if (req.getUsername() == null || req.getPassword() == null) {
            throw new InvalidRequestException("Missing username or password");
        }

        User user;
        try {
            user = userService.createNewUser(req);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidUserException("Could not create the user");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        Principal principal = new Principal(user.getUserId(), user.getUsername(), user.getActive(), user.getUserRole().getRole());
        tokenService.createNewToken(principal);

        return principal;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<String> getUsernames(HttpServletRequest servReq) {
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
            return userService.getUsernames();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidUserException.class)
    public InvalidUserException handledUserException (InvalidUserException e) {
        logger.info(e.getMessage());
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
