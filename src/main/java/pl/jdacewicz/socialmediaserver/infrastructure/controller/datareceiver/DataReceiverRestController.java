package pl.jdacewicz.socialmediaserver.infrastructure.controller.datareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jdacewicz.socialmediaserver.datareceiver.DataReceiverFacade;
import pl.jdacewicz.socialmediaserver.datareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.datareceiver.dto.PostRequest;

@RestController
@RequestMapping(value = "/api/data/receivers")
@RequiredArgsConstructor
public class DataReceiverRestController {

    private final DataReceiverFacade dataReceiverFacade;

    @PostMapping("/posts")
    public PostDto createPost(PostRequest postRequest) {
        return dataReceiverFacade.createPost(postRequest);
    }
}
