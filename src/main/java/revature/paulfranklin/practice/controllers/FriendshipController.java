package revature.paulfranklin.practice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import revature.paulfranklin.practice.dtos.requests.NewFriendRequest;
import revature.paulfranklin.practice.dtos.responses.Principal;
import revature.paulfranklin.practice.entities.User;
import revature.paulfranklin.practice.exceptions.InvalidAuthException;
import revature.paulfranklin.practice.exceptions.InvalidFriendshipException;
import revature.paulfranklin.practice.exceptions.InvalidRequestException;
import revature.paulfranklin.practice.exceptions.InvalidUserException;
import revature.paulfranklin.practice.services.FriendshipService;
import revature.paulfranklin.practice.services.TokenService;
import revature.paulfranklin.practice.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/friendships")
public class FriendshipController {
    private final Logger logger = LoggerFactory.getLogger(FriendshipController.class);
    private final FriendshipService friendshipService;
    private final TokenService tokenService;
    private final UserService userService;

    public FriendshipController(FriendshipService friendshipService, TokenService tokenService, UserService userService) {
        this.friendshipService = friendshipService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<String> getFriends(HttpServletRequest servReq) {
        String token = servReq.getHeader("authorization");
        if (token == null || token.isEmpty()) {
            throw new InvalidRequestException("Missing token");
        }

        Principal principal = tokenService.retrievePrincipalFromToken(token);

        try {
            User user = userService.getUserByUsername(principal.getUsername());

            if (user == null) {
                throw new InvalidAuthException("User was not found");
            }
        } catch (InvalidAuthException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            return friendshipService.getFriendNamesByUserId(principal.getUserId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newFriend(@RequestBody NewFriendRequest req, HttpServletRequest servReq) {
        String token = servReq.getHeader("authorization");
        if (token == null || token.isEmpty()) {
            throw new InvalidRequestException("Missing token");
        }
        if (req.getFriendName() == null) {
            throw new InvalidRequestException("Missing friend name");
        }

        Principal principal = tokenService.retrievePrincipalFromToken(token);

        try {
            User user = userService.getUserByUsername(principal.getUsername());

            if (user == null) {
                throw new InvalidAuthException("User was not found");
            }
        } catch (InvalidAuthException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            User user = userService.getUserByUsername(principal.getUsername());
            User friend = userService.getUserByUsername(req.getFriendName());

            if (user == null) {
                throw new InvalidUserException("User was not found");
            }
            if (friend == null) {
                throw new InvalidUserException("Friend was not found");
            }

            friendshipService.createNewFriendship(user, friend);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidFriendshipException("Could not create the friendship");
        } catch (IllegalArgumentException e) {
            throw new InvalidFriendshipException(e.getMessage());
        } catch (InvalidUserException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("delete/{friendName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteFriend(@PathVariable(name="friendName") String friendName, HttpServletRequest servReq) {
        String token = servReq.getHeader("authorization");
        if (token == null || token.isEmpty()) {
            throw new InvalidRequestException("Missing token");
        }

        Principal principal = tokenService.retrievePrincipalFromToken(token);

        try {
            User user = userService.getUserByUsername(principal.getUsername());

            if (user == null) {
                throw new InvalidAuthException("User was not found");
            }
        } catch (InvalidAuthException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            friendshipService.deleteFriendship(principal.getUserId(), friendName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    public InvalidRequestException handledRequestException (InvalidRequestException e) {
        logger.error(e.getMessage());
        return e;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidFriendshipException.class)
    public InvalidFriendshipException handledFriendshipException (InvalidFriendshipException e) {
        logger.info(e.getMessage());
        return e;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUserException.class)
    public InvalidUserException handledUserException (InvalidUserException e) {
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
