package pl.jdacewicz.socialmediaserver.infrastructure.controller.datasearcher;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.jdacewicz.socialmediaserver.datasearcher.DataSearcherFacade;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

@RestController
@RequestMapping(value = "/api/search")
@RequiredArgsConstructor
public class DataSearcherRestController {

    private final DataSearcherFacade dataSearcherFacade;

    @GetMapping
    public SearchResult searchData(@RequestParam @NotBlank String scope,
                                   @RequestParam int pageNumber,
                                   @RequestParam int pageSize,
                                   @RequestParam @Valid String phrase) {
        return dataSearcherFacade.searchData(scope, pageNumber, pageSize, phrase);
    }
}
