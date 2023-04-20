package revature.paulfranklin.practice.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import revature.paulfranklin.practice.dtos.requests.NewLoginRequest;
import revature.paulfranklin.practice.dtos.requests.NewUserRequest;
import revature.paulfranklin.practice.entities.User;
import revature.paulfranklin.practice.entities.UserRole;
import revature.paulfranklin.practice.enums.Role;
import revature.paulfranklin.practice.repositories.UserRepository;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createNewUser(NewUserRequest req) throws SQLException {
        User user = new User(UUID.randomUUID().toString(), req.getUsername(), req.getPassword(), true, new UserRole(Role.USER));

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException(e);
        }

        return user;
    }

    public Optional<User> getUser(NewLoginRequest req) throws SQLException {
        return getUserByUsername(req.getUsername());
    }

    public List<String> getUsernames() throws SQLException {
        List<User> users;
        try {
            users = userRepository.findAll();
        } catch (Exception e) {
            throw new SQLException(e);
        }

        List<String> usernames = new LinkedList<>();
        users.forEach(user -> usernames.add(user.getUsername()));

        return usernames;
    }

    public Optional<User> getUserByUsername(String username) throws SQLException {
        try {
            return Optional.ofNullable(userRepository.findByUsername(username));
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public boolean badLoginPassword(User user, NewLoginRequest req) {
        return !user.getPassword().equals(req.getPassword());
    }
}
