package pl.jdacewicz.socialmediaserver.infrastructure.controller.reactiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.ReactionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionRequest;

@RestController
@RequestMapping(value = "/api/reactions")
@RequiredArgsConstructor
public class ReactionDataReceiverRestController {

    private final ReactionDataReceiverFacade reactionDataReceiverFacade;

    @GetMapping("/{id}")
    public ReactionDto getReactionById(@PathVariable String id) {
        return reactionDataReceiverFacade.getReactionById(id);
    }

    @PostMapping
    public ReactionDto createReaction(@RequestBody ReactionRequest reactionRequest) {
        return reactionDataReceiverFacade.createReaction(reactionRequest);
    }
}
