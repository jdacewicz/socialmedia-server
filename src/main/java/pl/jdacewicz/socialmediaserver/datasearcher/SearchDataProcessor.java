package pl.jdacewicz.socialmediaserver.datasearcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class SearchDataProcessor {

    static Set<String> splitTextToUniqueWords(String text) {
        var words = text.split("\\s+");
        var list = Arrays.asList(words);
        return new HashSet<>(list);
    }
}
