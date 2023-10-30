package pl.jdacewicz.socialmediaserver.datasearcher.dto;

public record SearchRequest(SearchRequestType type,
                            String typedInText) {

    public enum SearchRequestType {
        ALL,
        USERS,
        POSTS,
        COMMENTS
    }
}
