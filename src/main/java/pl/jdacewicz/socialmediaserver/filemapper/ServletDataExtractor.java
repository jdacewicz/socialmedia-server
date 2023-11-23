package pl.jdacewicz.socialmediaserver.filemapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ServletDataExtractor {

    private final HttpServletRequest httpServletRequest;

    String getFullServerUrl() {
        var serverName = httpServletRequest.getServerName();
        var serverPort = httpServletRequest.getServerPort();
        var scheme = httpServletRequest.getScheme();
        return scheme + "://" + serverName + ":" + serverPort;
    }
}
