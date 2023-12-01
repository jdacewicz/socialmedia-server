package pl.jdacewicz.socialmediaserver.datagrouper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "post_groups")
@Getter
@SuperBuilder
@NoArgsConstructor
class PostGroup extends Group {

    final static String MAIN_DIRECTORY = "post-groups";

    @Override
    String getFolderDirectory() {
        return String.format("%s/%s/%s", Group.MAIN_DIRECTORY, MAIN_DIRECTORY, getGroupId());
    }
}
