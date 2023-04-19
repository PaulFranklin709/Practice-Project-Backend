package revature.paulfranklin.practice.services;

import org.springframework.stereotype.Service;
import revature.paulfranklin.practice.entities.UserRole;
import revature.paulfranklin.practice.enums.Role;
import revature.paulfranklin.practice.repositories.UserRoleRepository;

import java.sql.SQLException;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) throws SQLException {
        this.userRoleRepository = userRoleRepository;
        try {
            userRoleRepository.save(new UserRole(Role.USER));
            userRoleRepository.save(new UserRole(Role.ADMIN));
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
}
