package revature.paulfranklin.practice.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import revature.paulfranklin.practice.dtos.requests.DeleteFriendRequest;
import revature.paulfranklin.practice.dtos.requests.NewFriendRequest;
import revature.paulfranklin.practice.dtos.responses.Principal;
import revature.paulfranklin.practice.entities.User;
import revature.paulfranklin.practice.exceptions.InvalidFriendshipException;
import revature.paulfranklin.practice.exceptions.InvalidRequestException;
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
            User friend = userService.getUserByUsername(req.getFriendName());

            friendshipService.createNewFriendship(user, friend);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidFriendshipException("Could not create the friendship");
        } catch (IllegalArgumentException e) {
            throw new InvalidFriendshipException(e.getMessage());
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
        if (friendName == null) {
            throw new InvalidRequestException("Missing friend name");
        }

        Principal principal = tokenService.retrievePrincipalFromToken(token);

        try {
            friendshipService.deleteFriendship(principal.getUserId(), friendName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    public InvalidRequestException handledRequestException (InvalidRequestException e) {
        return e;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidFriendshipException.class)
    public InvalidFriendshipException handledFriendshipException (InvalidFriendshipException e) {
        return e;
    }
}
