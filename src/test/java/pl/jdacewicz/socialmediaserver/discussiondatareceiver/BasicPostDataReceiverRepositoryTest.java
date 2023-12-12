package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

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
class BasicPostDataReceiverRepositoryTest implements BasicPostDataReceiverRepository {

    List<BasicPost> database = new LinkedList<>();

    @Override
    public <S extends BasicPost> S save(S entity) {
        database.add(entity);
        return entity;
    }

    @Override
    public Optional<BasicPost> findById(String s) {
        return database.stream()
                .filter(post -> post.getDiscussionId()
                        .equals(s))
                .findFirst();
    }

    @Override
    public Page<BasicPost> findByContentContaining(String phrase, Pageable pageable) {
        return null;
    }

    @Override
    public Page<BasicPost> findAllByCreator_UserId(String userId, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends BasicPost> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends BasicPost> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends BasicPost> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends BasicPost> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends BasicPost> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends BasicPost> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends BasicPost> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends BasicPost> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends BasicPost, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends BasicPost> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<BasicPost> findAll() {
        return null;
    }

    @Override
    public List<BasicPost> findAllById(Iterable<String> strings) {
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
    public void delete(BasicPost entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends BasicPost> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<BasicPost> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<BasicPost> findAll(Pageable pageable) {
        return null;
    }
}