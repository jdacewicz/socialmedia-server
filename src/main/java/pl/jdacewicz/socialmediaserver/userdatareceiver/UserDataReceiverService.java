package pl.jdacewicz.socialmediaserver.userdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserBan;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
class UserDataReceiverService implements UserDetailsService {

    private final UserDataReceiverRepository userDataReceiverRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return getUserByEmail(username);
    }

    @Transactional
    public void banUser(String userId, String banId) {
        var userBan = new UserBan(banId);
        var bannedUser = getUserById(userId).banUser(userBan);
        userDataReceiverRepository.save(bannedUser);
    }

    @Transactional
    public void unbanUser(String userId) {
        var unbannedUser = getUserById(userId).unbanUser();
        userDataReceiverRepository.save(unbannedUser);
    }

    @Transactional
    public void unbanUsers(Set<String> userIds) {
        var unbannedUsers = getUsersByIds(userIds).stream()
                .map(User::unbanUser)
                .toList();
        userDataReceiverRepository.saveAll(unbannedUsers);
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
                .orElseThrow(() -> new UserNotFoundException("Could not find user with username: " + email));
    }

    User getUserById(String userId) {
        return userDataReceiverRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Could not find user with id: " + userId));
    }

    List<User> getUsersByIds(Set<String> userIds) {
        return userDataReceiverRepository.findAllById(userIds);
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

    void deleteAllUsers() {
        userDataReceiverRepository.deleteAll();
    }
}
