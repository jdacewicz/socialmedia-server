package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.dto.BanWordRequest;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.dto.BannedWordDto;

@RequiredArgsConstructor
public class BannedWordsCheckerFacade {

    private final BannedWordService bannedWordService;
    private final BannedWordMapper bannedWordMapper;

    public Page<BannedWordDto> getBannedWords(int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var bannedWords = bannedWordService.getBannedWords(pageable);
        return bannedWords.map(bannedWordMapper::mapToDto);
    }

    public BannedWordDto getBannedWordByWord(String word) {
        var bannedWord = bannedWordService.getBannedWordByWord(word);
        return bannedWordMapper.mapToDto(bannedWord);
    }

    public void checkForBannedWords(String text) {
        var bannedWords = bannedWordService.getAllBannedWords();
        if (BannedWordsChecker.doesTextContainBannedWords(text, bannedWords)) {
            throw new UnsupportedOperationException();
        }
    }

    public BannedWordDto createBannedWord(String authenticationHeader, BanWordRequest banWordRequest) {
        var createdBannedWord = bannedWordService.createBannedWord(authenticationHeader, banWordRequest);
        return bannedWordMapper.mapToDto(createdBannedWord);
    }

    public void deleteBannedWord(String wordId) {
        bannedWordService.deleteBannedWordsById(wordId);
    }
}
