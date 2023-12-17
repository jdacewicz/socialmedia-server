package pl.jdacewicz.socialmediaserver.datasearcher;

enum SearchRequestScope {
    ALL,
    USERS,
    POSTS;

    static SearchRequestScope getScope(String scope) {
        return valueOf(scope.toUpperCase());
    }
}
