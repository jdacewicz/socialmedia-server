package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import java.util.List;

class BannedWordsChecker {

    static boolean doesTextContainBannedWords(String textToCheck, List<BannedWord> bannedWords) {
        return bannedWords.stream()
                .anyMatch(bannedWord -> textToCheck.contains(bannedWord.word()));
    }
}
