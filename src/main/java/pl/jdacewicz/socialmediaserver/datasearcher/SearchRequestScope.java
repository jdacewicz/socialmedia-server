package pl.jdacewicz.socialmediaserver.datasearcher;

enum SearchRequestScope {
    ALL,
    USERS,
    POSTS,
    BANNED_WORDS,
    REACTIONS;

    static SearchRequestScope getScope(String scope) {
        return valueOf(scope.toUpperCase());
    }
}
