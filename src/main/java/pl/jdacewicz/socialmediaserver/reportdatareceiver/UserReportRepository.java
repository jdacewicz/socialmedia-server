package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

interface UserReportRepository extends MongoRepository<UserReport, String> {
}
