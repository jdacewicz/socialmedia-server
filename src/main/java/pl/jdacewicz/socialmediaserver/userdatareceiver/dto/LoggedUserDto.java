package pl.jdacewicz.socialmediaserver.userdatareceiver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class LoggedUserDto extends UserDto {

    private String dataDirectory;
}
