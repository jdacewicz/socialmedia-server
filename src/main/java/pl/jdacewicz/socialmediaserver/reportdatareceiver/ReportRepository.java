package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface ReportRepository extends MongoRepository<Report, String> {

    Page<Report> findAllByDataType(DataType dataType, Pageable pageable);
}
