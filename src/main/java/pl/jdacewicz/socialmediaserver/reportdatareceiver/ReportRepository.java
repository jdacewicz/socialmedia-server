package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface ReportRepository extends MongoRepository<Report, String> {

    List<Report> findAllByDataType(DataType dataType);
}
