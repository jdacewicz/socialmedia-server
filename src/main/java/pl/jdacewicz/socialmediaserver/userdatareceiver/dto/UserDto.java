package pl.jdacewicz.socialmediaserver.userdatareceiver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.jdacewicz.socialmediaserver.filemapper.dto.File;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userId;
    private String fullName;
    private File profilePicture;
}
