package pl.jdacewicz.socialmediaserver.userdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;

@Service
@RequiredArgsConstructor
class UserDataReceiverService implements UserDetailsService {

    private final UserDataReceiverRepository userDataReceiverRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDataReceiverRepository.findByEmail(username)
                .orElseThrow(UnsupportedOperationException::new);
    }

    User getUserByEmail(String email) {
        return userDataReceiverRepository.findByEmail(email)
                .orElseThrow(UnsupportedOperationException::new);
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
