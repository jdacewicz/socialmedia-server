package pl.jdacewicz.socialmediaserver.bangiver;

import org.springframework.data.mongodb.repository.MongoRepository;

interface TemporaryBanRepository extends MongoRepository<TemporaryBan, String> {

    void updateByBanId(String banId, boolean expired);
}
