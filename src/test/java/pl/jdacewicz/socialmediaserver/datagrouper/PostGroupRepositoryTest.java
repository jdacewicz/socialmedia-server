package pl.jdacewicz.socialmediaserver.datagrouper;

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
class PostGroupRepositoryTest implements PostGroupRepository {

    List<PostGroup> database = new LinkedList<>();

    @Override
    public Optional<PostGroup> findById(String groupId) {
        return database.stream()
                .filter(postGroup -> postGroup.getGroupId()
                        .equals(groupId))
                .findFirst();
    }

    @Override
    public <S extends PostGroup> S save(S entity) {
        database.add(entity);
        return entity;
    }

    @Override
    public Page<PostGroup> findByParticipants(String participantId, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public <S extends PostGroup> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends PostGroup> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends PostGroup> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends PostGroup> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PostGroup, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends PostGroup> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends PostGroup> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PostGroup> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends PostGroup> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends PostGroup> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<PostGroup> findAll() {
        return null;
    }

    @Override
    public List<PostGroup> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(PostGroup entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends PostGroup> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<PostGroup> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<PostGroup> findAll(Pageable pageable) {
        return null;
    }
}