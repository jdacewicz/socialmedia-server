package pl.jdacewicz.socialmediaserver.bannedwordschecker.dto;

import lombok.Builder;

@Builder
public record BannedWordDto(String wordId,
                            String word) {
}
