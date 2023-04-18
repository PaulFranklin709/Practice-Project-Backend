package revature.paulfranklin.practice.services;

import org.springframework.stereotype.Service;
import revature.paulfranklin.practice.dtos.requests.NewUserRequest;
import revature.paulfranklin.practice.entities.User;
import revature.paulfranklin.practice.entities.UserRole;
import revature.paulfranklin.practice.enums.Role;
import revature.paulfranklin.practice.repositories.UserRepository;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createNewUser(NewUserRequest req) {
        User user = new User(UUID.randomUUID().toString(), req.getUsername(), req.getPassword(), true, new UserRole(Role.USER));
        userRepository.save(user);
    }
}
