package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

interface CommentReportRepository extends MongoRepository<CommentReport, String> {
}
