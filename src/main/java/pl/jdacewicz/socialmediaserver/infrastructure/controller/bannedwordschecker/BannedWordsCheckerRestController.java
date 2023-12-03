package pl.jdacewicz.socialmediaserver.infrastructure.controller.bannedwordschecker;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.dto.BanWordRequest;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.dto.BannedWordDto;

@RestController
@RequestMapping(value = "/api/banned-words")
@RequiredArgsConstructor
public class BannedWordsCheckerRestController {

    private final BannedWordsCheckerFacade bannedWordsCheckerFacade;

    @GetMapping
    public Page<BannedWordDto> getBannedWords(@RequestParam int pageNumber,
                                              @RequestParam int pageSize) {
        return bannedWordsCheckerFacade.getBannedWords(pageNumber, pageSize);
    }

    @PostMapping
    public BannedWordDto createBannedWord(@RequestHeader("Authorization") String authorizationHeader,
                                          @RequestBody BanWordRequest banWordRequest) {
        return bannedWordsCheckerFacade.createBannedWord(authorizationHeader, banWordRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteBannedWord(@PathVariable String id) {
        bannedWordsCheckerFacade.deleteBannedWord(id);
    }
}
