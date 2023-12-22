package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("all")
class ReactionDataReceiverRepositoryTest implements ReactionDataReceiverRepository {

    List<Reaction> database = new LinkedList<>();

    @Override
    public Optional<Reaction> findById(String reactionId) {
        return database.stream()
                .filter(reaction -> reaction.reactionId().equals(reactionId))
                .findFirst();
    }

    @Override
    public Reaction save(Reaction entity) {
        database.add(entity);
        return entity;
    }

    @Override
    public void updateReactionName(String id, String name) {
        int index = database.indexOf(id);
        if (index >= 0) {
            database.stream()
                    .filter(reaction -> reaction.reactionId().equals(id))
                    .findFirst()
                    .ifPresent(reaction -> {
                        var updatedReaction = Reaction.builder()
                                .reactionId(reaction.reactionId())
                                .name(name)
                                .imageName(reaction.imageName())
                                .active(reaction.active())
                                .archived(reaction.archived())
                                .build();
                        database.set(index, updatedReaction);
                    });
        }
    }

    @Override
    public void updateReactionActive(String id, boolean active) {
        int index = database.indexOf(id);
        if (index >= 0) {
            database.stream()
                    .filter(reaction -> reaction.reactionId().equals(id))
                    .findFirst()
                    .ifPresent(reaction -> {
                        var updatedReaction = Reaction.builder()
                                .reactionId(reaction.reactionId())
                                .name(reaction.name())
                                .imageName(reaction.imageName())
                                .archived(reaction.archived())
                                .active(active)
                                .build();
                        database.set(index, reaction);
                    });
        }
    }

    @Override
    public void updateReactionArchived(String id, boolean archived) {
        int index = database.indexOf(id);
        if (index >= 0) {
            database.stream()
                    .filter(reaction -> reaction.reactionId().equals(id))
                    .findFirst()
                    .ifPresent(reaction -> {
                        var updatedReaction = Reaction.builder()
                                .reactionId(reaction.reactionId())
                                .name(reaction.name())
                                .imageName(reaction.imageName())
                                .active(reaction.active())
                                .archived(archived)
                                .build();
                        database.set(index, reaction);
                    });
        }
    }

    @Override
    public Page<Reaction> findByArchived(boolean archived, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Reaction> findByActiveAndArchived(boolean active, boolean archived, Pageable pageable) {
        return null;
    }

    @Override
    public List<Reaction> findAllByActiveAndArchived(boolean active, boolean archived) {
        return null;
    }

    @Override
    public Page<Reaction> findByNameContaining(String name, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Reaction> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Reaction> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Reaction> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Reaction> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Reaction> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Reaction> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Reaction> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Reaction> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Reaction, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Reaction> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Reaction> findAll() {
        return null;
    }

    @Override
    public List<Reaction> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Reaction entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Reaction> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Reaction> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Reaction> findAll(Pageable pageable) {
        return null;
    }
}
