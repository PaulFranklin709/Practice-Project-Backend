package revature.paulfranklin.practice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import revature.paulfranklin.practice.dtos.requests.NewLoginRequest;
import revature.paulfranklin.practice.dtos.responses.Principal;
import revature.paulfranklin.practice.entities.User;
import revature.paulfranklin.practice.exceptions.InvalidAuthException;
import revature.paulfranklin.practice.services.TokenService;
import revature.paulfranklin.practice.services.UserService;

import java.sql.SQLException;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Principal login(@RequestBody NewLoginRequest req) {
        if (req.getUsername() == null || req.getPassword() == null) {
            throw new RuntimeException("Missing username or password");
        }

        User user;
        try {
            user = userService.getUser(req);

            if (user == null) {
                throw new InvalidAuthException("User was not found");
            }

            if (!user.getPassword().equals(req.getPassword())) {
                throw new InvalidAuthException("Wrong password");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        Principal principal = new Principal(user.getUserId(), user.getUsername(), user.getActive(), user.getUserRole().getRole());
        tokenService.createNewToken(principal);

        return principal;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidAuthException.class)
    public InvalidAuthException handledAuthException (InvalidAuthException e) {
        return e;
    }
}
