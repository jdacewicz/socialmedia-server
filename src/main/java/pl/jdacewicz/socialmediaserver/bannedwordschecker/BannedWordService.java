package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.dto.BanWordRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
class BannedWordService {

    private final BannedWordRepository bannedWordRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;

    List<BannedWord> getAllBannedWords() {
       return bannedWordRepository.findAll();
    }

    Page<BannedWord> getBannedWords(Pageable pageable) {
        return bannedWordRepository.findAll(pageable);
    }

    BannedWord getBannedWordByWord(String word) {
        return bannedWordRepository.findByWord(word)
                .orElseThrow(UnsupportedOperationException::new);
    }

    BannedWord createBannedWord(String authenticationHeader, BanWordRequest banWordRequest) {
        var loggedUserId = userDataReceiverFacade.getLoggedInUser(authenticationHeader)
                .getUserId();
        var bannedWord = BannedWord.builder()
                .word(banWordRequest.word())
                .creatorId(loggedUserId)
                .build();
        return bannedWordRepository.save(bannedWord);
    }

    void deleteBannedWordsById(String id) {
        bannedWordRepository.deleteById(id);
    }

    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    void deleteAllBannedWords() {
        bannedWordRepository.deleteAll();
    }
}
