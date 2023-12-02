package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.filemapper.FileMapperFacade;
import pl.jdacewicz.socialmediaserver.filemapper.dto.MapRequest;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;

import java.util.List;

@Component
@RequiredArgsConstructor
class ReactionMapper {

    private final FileMapperFacade fileMapperFacade;

    List<ReactionDto> mapToDto(List<Reaction> reactions) {
      return reactions.stream()
              .map(this::mapToDto)
              .toList();
    }

    ReactionDto mapToDto(Reaction reaction) {
        var image = fileMapperFacade.mapToFile(new MapRequest(reaction.imageName(), reaction.getReactionFolderDirectory()));
        return ReactionDto.builder()
                .reactionId(reaction.reactionId())
                .name(reaction.name())
                .image(image)
                .build();
    }
}
