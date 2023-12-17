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
class CommentDataReceiverRepositoryTest implements CommentDataReceiverRepository {

    List<Comment> database = new LinkedList<>();

    @Override
    public <S extends Comment> S save(S entity) {
        database.add(entity);
        return entity;
    }

    @Override
    public Optional<Comment> findById(String s) {
        return database.stream()
                .filter(comment -> comment.getDiscussionId()
                        .equals(s))
                .findFirst();
    }

    @Override
    public List<Comment> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public Page<Comment> findByContentContaining(String phrase, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Comment> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Comment> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Comment> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Comment> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Comment> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Comment> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Comment> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Comment> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Comment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Comment> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Comment> findAll() {
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
    public void delete(Comment entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Comment> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Comment> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return null;
    }
}