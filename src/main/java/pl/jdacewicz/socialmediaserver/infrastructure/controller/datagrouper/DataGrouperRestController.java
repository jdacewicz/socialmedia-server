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

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/groups")
@RequiredArgsConstructor
public class DataGrouperRestController {

    private final DataGrouperFacade dataGrouperFacade;

    @GetMapping("/{id}")
    public GroupDto getGroupById(@PathVariable @NotBlank String id,
                                 @RequestParam @NotBlank String type) {
        return dataGrouperFacade.getGroupById(id, type);
    }

    @GetMapping("/participant/{participantId}")
    public Page<GroupDto> getGroupsByParticipantId(@PathVariable @NotBlank String participantId,
                                                   @RequestParam @NotBlank String type,
                                                   @RequestParam int pageNumber,
                                                   @RequestParam int pageSize) {
        return dataGrouperFacade.getGroupsByParticipantId(participantId, type, pageNumber, pageSize);
    }

    @PostMapping
    public GroupDto createGroup(@RequestHeader("Authorization") String authorizationHeader,
                                @RequestParam @NotBlank String type,
                                @RequestPart MultipartFile groupImage,
                                @RequestPart @Valid GroupRequest groupRequest) throws IOException {
        return dataGrouperFacade.createGroup(groupImage, authorizationHeader, groupRequest, type);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable @NotBlank String id,
                            @RequestParam @NotBlank String type) throws IOException {
        dataGrouperFacade.deleteGroupById(id, type);
    }
}
