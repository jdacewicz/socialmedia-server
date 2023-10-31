package pl.jdacewicz.socialmediaserver.datasearcher;

enum SearchRequestScope {
    ALL,
    USERS,
    POSTS,
    COMMENTS;

    static SearchRequestScope getScope(String scope) {
        return valueOf(scope.toUpperCase());
    }
}
