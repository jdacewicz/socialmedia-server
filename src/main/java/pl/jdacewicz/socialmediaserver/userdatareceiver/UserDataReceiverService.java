package pl.jdacewicz.socialmediaserver.userdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
class UserDataReceiverService implements UserDetailsService {

    private final UserDataReceiverRepository userDataReceiverRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByEmail(username);
    }

    User getLoggedInUser() {
        var authentication = Optional.ofNullable(SecurityContextHolder
                .getContext()
                .getAuthentication());
        if (authentication.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return (User) authentication.get()
                .getPrincipal();
    }

    User getUserByEmail(String email) {
        return userDataReceiverRepository.findByEmail(email)
                .orElseThrow(UnsupportedOperationException::new);
    }

    Set<User> getUsersByFirstnamesAndLastnames(Set<String> firstnames, Set<String> lastnames) {
        return userDataReceiverRepository.findAllByFirstnameInAndLastnameIn(firstnames, lastnames);
    }

    User createUser(RegisterRequest registerRequest) {
        var user = User.builder()
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .build();
        return userDataReceiverRepository.save(user);
    }
}
