package pl.jdacewicz.socialmediaserver.infrastructure.controller.datagrouper;

import lombok.RequiredArgsConstructor;
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
    public GroupDto getGroupById(@PathVariable String id,
                                 @RequestParam String type) {
        return dataGrouperFacade.getGroupById(id, type);
    }

    @PostMapping
    public GroupDto createGroup(@RequestParam String type,
                                @RequestPart MultipartFile groupImage,
                                @RequestPart GroupRequest groupRequest) throws IOException {
        return dataGrouperFacade.createGroup(groupImage, groupRequest, type);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable String id,
                            @RequestParam String type) throws IOException {
        dataGrouperFacade.deleteGroupById(id, type);
    }
}
