package revature.paulfranklin.practice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import revature.paulfranklin.practice.dtos.responses.Principal;
import revature.paulfranklin.practice.entities.Friendship;
import revature.paulfranklin.practice.services.FriendshipService;
import revature.paulfranklin.practice.services.TokenService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/friendships")
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final TokenService tokenService;

    public FriendshipController(FriendshipService friendshipService, TokenService tokenService) {
        this.friendshipService = friendshipService;
        this.tokenService = tokenService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<String> getFriends(HttpServletRequest servReq) {
        String token = servReq.getHeader("authorization");

        Principal principal = tokenService.retrievePrincipalFromToken(token);

        return friendshipService.getFriendsByUserId(principal.getUserId());
    }
}
