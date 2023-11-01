package pl.jdacewicz.socialmediaserver.datagrouper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DataGrouperService {

    private final PostGroupRepository postGroupRepository;

    PostGroup getPostGroupById(String groupId) {
        return postGroupRepository.findById(groupId)
                .orElseThrow(UnsupportedOperationException::new);
    }
}
