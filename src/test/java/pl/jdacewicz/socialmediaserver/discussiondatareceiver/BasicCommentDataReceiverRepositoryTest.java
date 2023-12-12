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
class BasicCommentDataReceiverRepositoryTest implements BasicCommentDataReceiverRepository {

    List<BasicComment> database = new LinkedList<>();

    @Override
    public <S extends BasicComment> S save(S entity) {
        database.add(entity);
        return entity;
    }

    @Override
    public Optional<BasicComment> findById(String s) {
        return database.stream()
                .filter(comment -> comment.getDiscussionId()
                        .equals(s))
                .findFirst();
    }

    @Override
    public List<BasicComment> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public Page<BasicComment> findByContentContaining(String phrase, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends BasicComment> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends BasicComment> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends BasicComment> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends BasicComment> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends BasicComment> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends BasicComment> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends BasicComment> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends BasicComment> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends BasicComment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends BasicComment> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<BasicComment> findAll() {
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
    public void delete(BasicComment entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends BasicComment> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<BasicComment> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<BasicComment> findAll(Pageable pageable) {
        return null;
    }
}