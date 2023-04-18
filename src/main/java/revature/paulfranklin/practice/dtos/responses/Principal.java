package revature.paulfranklin.practice.dtos.responses;

public class Principal {
    private String userId;
    private String username;
    private Boolean isActive;
    private String role;
    private String token;

    public Principal() {
        super();
    }

    public Principal(String userId, String username, Boolean isActive, String role) {
        this.userId = userId;
        this.username = username;
        this.isActive = isActive;
        this.role = role;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
