package pl.jdacewicz.socialmediaserver.reportdatareceiver;

enum ReportType {
    NUDITY,
    SPAM,
    FAKE_NEWS,
    TERRORISM,
    SELF_HARM,
    PERSECUTE,
    DRASTIC_CONTENT,
    ILLEGAL_CONTENT;

    static ReportType getType(String type) {
        return valueOf(type.toUpperCase());
    }
}
