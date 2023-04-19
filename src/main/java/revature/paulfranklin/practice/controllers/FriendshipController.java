package revature.paulfranklin.practice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import revature.paulfranklin.practice.dtos.requests.NewFriendRequest;
import revature.paulfranklin.practice.dtos.responses.Principal;
import revature.paulfranklin.practice.entities.User;
import revature.paulfranklin.practice.services.FriendshipService;
import revature.paulfranklin.practice.services.TokenService;
import revature.paulfranklin.practice.services.UserService;

import javax.servlet.http.HttpServletRequest;
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

        Principal principal = null;
        try {
            principal = tokenService.retrievePrincipalFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return friendshipService.getFriendsByUserId(principal.getUserId());
    }

    @PostMapping("new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newFriend(@RequestBody NewFriendRequest req, HttpServletRequest servReq) {
        String token = servReq.getHeader("authorization");
        if (token == null) {
            throw new RuntimeException("Missing token");
        }
        if (req.getFriendName() == null) {
            throw new RuntimeException("Missing friend name");
        }

        Principal principal = null;
        try {
            principal = tokenService.retrievePrincipalFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            User user = userService.getUserByUsername(principal.getUsername());
            User friend = userService.getUserByUsername(req.getFriendName());

            friendshipService.createNewFriendship(user, friend);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
