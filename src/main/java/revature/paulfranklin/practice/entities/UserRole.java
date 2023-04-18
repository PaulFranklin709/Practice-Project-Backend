package revature.paulfranklin.practice.entities;

import javax.persistence.*;

@Entity
@Table
public class UserRole {
    @Id
    private String roleId;

    @Column(unique = true, nullable = false)
    private String role;

    public UserRole() {
    }

    public UserRole(String roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
