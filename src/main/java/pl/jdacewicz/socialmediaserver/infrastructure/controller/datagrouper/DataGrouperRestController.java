package pl.jdacewicz.socialmediaserver.infrastructure.controller.datagrouper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.datagrouper.DataGrouperFacade;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupDto;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupRequest;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.GroupedPostCreationRequest;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.ReportDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/groups")
@RequiredArgsConstructor
public class DataGrouperRestController {

    final static String discussionType = "GROUPED_POST";

    private final DataGrouperFacade dataGrouperFacade;
    private final DiscussionDataReceiverFacade discussionDataReceiverFacade;
    private final ReportDataReceiverFacade reportDataReceiverFacade;

    @GetMapping("/{id}")
    public GroupDto getGroupById(@PathVariable @NotBlank String id,
                                 @RequestParam @NotBlank String type) {
        return dataGrouperFacade.getGroupById(id, type);
    }

    @GetMapping("/posts/{id}")
    public DiscussionDto getGroupedPostById(@PathVariable @NotBlank String id) {
        return discussionDataReceiverFacade.getDiscussionById(id, discussionType);
    }

    @GetMapping("/participants/{participantId}")
    public Page<GroupDto> getGroupsByParticipantId(@PathVariable @NotBlank String participantId,
                                                   @RequestParam @NotBlank String type,
                                                   @RequestParam int pageNumber,
                                                   @RequestParam int pageSize) {
        return dataGrouperFacade.getGroupsByParticipantId(participantId, type, pageNumber, pageSize);
    }

    @GetMapping("/posts/users/{userId}")
    public Page<DiscussionDto> getGroupedPostsByUserId(@PathVariable String userId,
                                                       @RequestParam int pageNumber,
                                                       @RequestParam int pageSize) {
        return discussionDataReceiverFacade.getPostsByUserId(userId, discussionType, pageNumber, pageSize);
    }

    @GetMapping("/posts")
    public List<DiscussionDto> getRandomGroupedPosts() {
        return discussionDataReceiverFacade.getRandomPosts(discussionType);
    }

    @GetMapping("/posts/{id}/comments")
    public Set<DiscussionDto> getCommentsByGroupedPostId(@PathVariable String id,
                                                         @RequestParam int commentQuantity) {
        return discussionDataReceiverFacade.getCommentsByPostId(id, discussionType, commentQuantity);
    }

    @PostMapping
    public GroupDto createGroup(@RequestHeader("Authorization") String authorizationHeader,
                                @RequestParam @NotBlank String type,
                                @RequestPart MultipartFile groupImage,
                                @RequestPart @Valid GroupRequest groupRequest) throws IOException {
        return dataGrouperFacade.createGroup(groupImage, authorizationHeader, groupRequest, type);
    }

    @PostMapping("/posts")
    public DiscussionDto createGroupedPost(@RequestHeader("Authorization") String authorizationHeader,
                                           @RequestPart MultipartFile postImage,
                                           @RequestPart @Valid GroupedPostCreationRequest groupedPostCreationRequest) throws IOException {
        return discussionDataReceiverFacade.createDiscussion(discussionType, authorizationHeader, postImage, groupedPostCreationRequest);
    }

    @PostMapping("/posts/{id}/report")
    public void reportPost(@PathVariable String id,
                           @RequestHeader("Authorization") String authorizationHeader,
                           @RequestBody ReportRequest reportRequest) {
        reportDataReceiverFacade.report(id, authorizationHeader, reportRequest, discussionType);
    }

    @PutMapping("/posts/{postId}/react/{reactionId}")
    public DiscussionDto reactToGroupedPost(@RequestHeader("Authorization") String authorizationHeader,
                                            @PathVariable @NotBlank String postId,
                                            @PathVariable @NotBlank String reactionId) {
        return discussionDataReceiverFacade.reactToDiscussion(reactionId, postId, discussionType, authorizationHeader);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable @NotBlank String id,
                            @RequestParam @NotBlank String type) throws IOException {
        dataGrouperFacade.deleteGroupById(id, type);
    }

    @DeleteMapping("/posts/{id}")
    public void deleteGroupedPost(@PathVariable @NotBlank String id) throws IOException {
        discussionDataReceiverFacade.deleteDiscussion(id, discussionType);
    }
}
