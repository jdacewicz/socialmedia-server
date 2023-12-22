package pl.jdacewicz.socialmediaserver.datasearcher.dto;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.Map;

@Builder
public record SearchResult(Map<String, Page<?>> results) {

    @SuppressWarnings("unused")
    public static class SearchResultBuilder {
        private Map<String, Page<?>> results = Map.of();
    }
}
