package epam.zlatamigas.surveyplatform.model.entity;


import java.time.LocalDate;

public class User extends AbstractEntity {

    private int userId;
    private String email;
    private String password;
    private LocalDate registrationDate;
    private UserRole role;
    private UserStatus status;

    public User() {
        userId = 0;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public static class UserBuilder {

        private final User user;

        public UserBuilder() {
            user = new User();
        }


        public UserBuilder setUserId(int userId) {
            user.userId = userId;
            return this;
        }


        public UserBuilder setEmail(String email) {
            user.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            user.password = password;
            return this;
        }

        public UserBuilder setRegistrationDate(LocalDate registrationDare) {
            user.registrationDate = registrationDare;
            return this;
        }

        public UserBuilder setRole(UserRole role) {
            user.role = role;
            return this;
        }

        public UserBuilder setStatus(UserStatus status) {
            user.status = status;
            return this;
        }

        public User getUser() {
            return user;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return userId == user.userId
                && email != null
                && email.equals(user.email)
                && password != null
                && password.equals(user.password)
                && registrationDate != null
                && registrationDate.equals(user.registrationDate)
                && role == user.role
                && status == user.status;
    }

    @Override
    public int hashCode() {
        int seed = 31;
        int hash = 1;

        hash = seed * hash + userId;
        hash = seed * hash + (email != null ? email.hashCode() : 0);
        hash = seed * hash + (password != null ? password.hashCode() : 0);
        hash = seed * hash + (registrationDate != null ? registrationDate.hashCode() : 0);
        hash = seed * hash + (role != null ? role.hashCode() : 0);
        hash = seed * hash + (status != null ? status.hashCode() : 0);

        return hash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId=").append(userId);
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", registrationDate=").append(registrationDate);
        sb.append(", role=").append(role);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
