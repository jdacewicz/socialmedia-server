package pl.jdacewicz.socialmediaserver.userdatareceiver;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserBan;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Document(collection = "users")
@Builder
record User(

        @Id
        String userId,

        @NotBlank
        @Email
        @Indexed(unique = true)
        String email,

        @NotBlank
        String password,

        @NotBlank
        @Size(min = 2, max = 16)
        String firstname,

        @NotBlank
        @Size(min = 2, max = 16)
        String lastname,

        String profilePictureName,

        boolean banned,

        List<UserBan> bans) implements UserDetails {

    final static String DEFAULT_USER_PROFILE_PICTURE_NAME = "default_user.png";
    final static String MAIN_DIRECTORY = "data/users";

    @SuppressWarnings("unused")
    static class UserBuilder {
        private String profilePictureName = DEFAULT_USER_PROFILE_PICTURE_NAME;
        private boolean banned = false;
        private List<UserBan> bans = new LinkedList<>();
    }

    User banUser(UserBan userBan) {
        var newUserBans = new LinkedList<>(this.bans);
        newUserBans.add(userBan);
        return User.builder()
                .userId(this.userId)
                .email(this.email)
                .password(this.password)
                .firstname(this.firstname)
                .lastname(this.lastname)
                .profilePictureName(this.profilePictureName)
                .banned(true)
                .bans(newUserBans)
                .build();
    }

    User unbanUser() {
        return User.builder()
                .userId(this.userId)
                .email(this.email)
                .password(this.password)
                .firstname(this.firstname)
                .lastname(this.lastname)
                .profilePictureName(this.profilePictureName)
                .bans(this.bans)
                .banned(false)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !banned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    String getFolderDirectory() {
        if (profilePictureName.equals(DEFAULT_USER_PROFILE_PICTURE_NAME)) {
            return MAIN_DIRECTORY;
        }
        return String.format("%s/%s", MAIN_DIRECTORY, userId);
    }
}
