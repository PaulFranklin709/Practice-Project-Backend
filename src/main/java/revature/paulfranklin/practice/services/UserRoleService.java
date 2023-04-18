package revature.paulfranklin.practice.services;

import org.springframework.stereotype.Service;
import revature.paulfranklin.practice.entities.UserRole;
import revature.paulfranklin.practice.repositories.UserRoleRepository;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
        userRoleRepository.save(new UserRole("0", "USER"));
        userRoleRepository.save(new UserRole("1", "ADMIN"));
    }
}
