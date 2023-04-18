package revature.paulfranklin.practice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import revature.paulfranklin.practice.dtos.requests.NewUserRequest;
import revature.paulfranklin.practice.dtos.responses.Principal;
import revature.paulfranklin.practice.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody NewUserRequest req) {
        if (req.getUsername() == null || req.getPassword() == null) {
            throw new RuntimeException("Missing username or password");
        }

        try {
            userService.createNewUser(req);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
