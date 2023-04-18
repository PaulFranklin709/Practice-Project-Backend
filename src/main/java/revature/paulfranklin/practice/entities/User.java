package revature.paulfranklin.practice.entities;

import javax.persistence.*;

@Entity
@Table(name = "_user")
public class User {
    @Id
    private String userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserRole userRole;

    public User() {
    }

    public User(String userId, String username, String password, Boolean isActive, UserRole userRole) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.userRole = userRole;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
