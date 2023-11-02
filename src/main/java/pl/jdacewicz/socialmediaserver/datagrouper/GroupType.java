package pl.jdacewicz.socialmediaserver.datagrouper;

enum GroupType {
    POST;

    static GroupType getType(String type) {
        return valueOf(type.toUpperCase());
    }
}
