package pl.jdacewicz.socialmediaserver.bangiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.jdacewicz.socialmediaserver.bangiver.dto.BannedUser;

import java.util.List;

interface TemporaryBanRepository extends MongoRepository<TemporaryBan, String> {

    List<TemporaryBan> findAllByBannedUserAndRevoked(BannedUser bannedUser, boolean revoked);

    List<TemporaryBan> findAllByExpiredAndRevoked(boolean expired, boolean revoked);
}
