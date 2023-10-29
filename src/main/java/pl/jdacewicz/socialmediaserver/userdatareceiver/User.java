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

import java.util.Collection;
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

            String profilePictureName) implements UserDetails {

    final static String DEFAULT_USER_PROFILE_PICTURE_NAME = "default_user.png";
    final static String MAIN_DIRECTORY = "data/users";

    static class UserBuilder {
        private String profilePictureName = DEFAULT_USER_PROFILE_PICTURE_NAME;
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
        return true;
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
