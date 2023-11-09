package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.dto.BannedWordDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class BannedWordMapper {

    Set<BannedWordDto> mapToDto(List<BannedWord> bannedWords) {
        return bannedWords.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    BannedWordDto mapToDto(BannedWord bannedWord) {
        return BannedWordDto.builder()
                .wordId(bannedWord.wordId())
                .word(bannedWord.word())
                .build();
    }
}
