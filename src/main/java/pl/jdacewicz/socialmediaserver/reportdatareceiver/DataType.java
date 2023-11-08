package pl.jdacewicz.socialmediaserver.reportdatareceiver;

enum DataType {
    USER,
    POST,
    COMMENT;

    static DataType getType(String type) {
        return valueOf(type.toUpperCase());
    }
}
