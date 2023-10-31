package pl.jdacewicz.socialmediaserver.infrastructure.controller.datasearcher;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jdacewicz.socialmediaserver.datasearcher.DataSearcherFacade;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchRequest;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

@RestController
@RequestMapping(value = "/api/search")
@RequiredArgsConstructor
public class DataSearcherRestController {

    private final DataSearcherFacade dataSearcherFacade;

    @GetMapping
    public SearchResult searchData(@RequestParam String scope,
                                   @RequestBody SearchRequest searchRequest) {
        return dataSearcherFacade.searchData(scope, searchRequest);
    }
}
