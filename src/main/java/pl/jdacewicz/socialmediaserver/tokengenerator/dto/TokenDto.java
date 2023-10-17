package pl.jdacewicz.socialmediaserver.tokengenerator.dto;

import lombok.Builder;

@Builder
public record TokenDto(String code,
                       boolean active) {
}
