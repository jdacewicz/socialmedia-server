package pl.jdacewicz.socialmediaserver.datagrouper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DataGrouperServiceFactoryImpl implements DataGrouperServiceFactory {

    private final PostGrouperService postGrouperService;

    @Override
    public DataGrouperService<? extends Group> getDataGrouperService(String type) {
        var groupType = GroupType.getType(type);
        switch (groupType) {
            case POST -> {
                return postGrouperService;
            }
            default -> throw new IllegalArgumentException("Invalid group type: " + groupType);
        }
    }
}
