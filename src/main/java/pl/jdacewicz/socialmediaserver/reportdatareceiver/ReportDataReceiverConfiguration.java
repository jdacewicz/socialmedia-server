package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReportDataReceiverConfiguration {

    @Bean
    ReportDataReceiverFacade reportDataReceiverFacade(ReportDataService reportDataService, ReportMapper reportMapper) {
        return new ReportDataReceiverFacade(reportDataService, reportMapper);
    }
}
