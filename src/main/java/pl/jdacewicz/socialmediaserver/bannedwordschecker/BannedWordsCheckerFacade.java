package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.dto.BanWordRequest;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.dto.BannedWordDto;

import java.util.Set;

@RequiredArgsConstructor
public class BannedWordsCheckerFacade {

    private final BannedWordService bannedWordService;
    private final BannedWordMapper bannedWordMapper;

    public void checkForBannedWords(String text) {
        var bannedWords = bannedWordService.getAllBannedWords();
        if (BannedWordsChecker.doesTextContainBannedWords(text, bannedWords)) {
            throw new UnsupportedOperationException();
        }
    }

    public Set<BannedWordDto> getBannedWords() {
        var bannedWords = bannedWordService.getAllBannedWords();
        return bannedWordMapper.mapToDto(bannedWords);
    }

    public BannedWordDto createBannedWords(String authenticationHeader, BanWordRequest banWordRequest) {
        var createdBannedWord = bannedWordService.createBannedWord(authenticationHeader, banWordRequest);
        return bannedWordMapper.mapToDto(createdBannedWord);
    }

    public void deleteBannedWord(String wordId) {
        bannedWordService.deleteBannedWordsById(wordId);
    }
}
