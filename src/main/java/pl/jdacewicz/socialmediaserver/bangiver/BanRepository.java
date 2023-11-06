package pl.jdacewicz.socialmediaserver.bangiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.jdacewicz.socialmediaserver.bangiver.dto.BannedUser;

import java.util.List;

interface BanRepository extends MongoRepository<Ban, String> {

    List<Ban> findAllByBannedUserAndRevoked(BannedUser bannedUser, boolean revoked);

    boolean existsByBannedUserAndRevoked(BannedUser bannedUser, boolean revoked);
}
