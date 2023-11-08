package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

interface PostReportRepository extends MongoRepository<PostReport, String> {
}
