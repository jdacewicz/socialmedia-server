package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import lombok.RequiredArgsConstructor;
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

    BannedWord createBannedWord(BanWordRequest banWordRequest) {
        var loggedUserId = userDataReceiverFacade.getLoggedInUser()
                .userId();
        var bannedWord = BannedWord.builder()
                .word(banWordRequest.word())
                .creatorId(loggedUserId)
                .build();
        return bannedWordRepository.save(bannedWord);
    }

    void deleteBannedWordsById(String id) {
        bannedWordRepository.deleteById(id);
    }
}
