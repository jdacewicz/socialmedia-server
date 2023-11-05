package pl.jdacewicz.socialmediaserver.bangiver;

import org.springframework.data.mongodb.repository.MongoRepository;

interface BanRepository extends MongoRepository<Ban, String> {

    void updateByBanId(String banId, boolean revoked);
}
