package pl.jdacewicz.socialmediaserver.bannedwordschecker;

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
class BannedWordRepositoryTest implements BannedWordRepository {

    List<BannedWord> database = new LinkedList<>();

    @Override
    public <S extends BannedWord> S save(S entity) {
        database.add(entity);
        return entity;
    }

    @Override
    public <S extends BannedWord> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends BannedWord> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends BannedWord> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends BannedWord> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends BannedWord> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends BannedWord> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends BannedWord> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends BannedWord> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends BannedWord, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends BannedWord> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<BannedWord> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<BannedWord> findAll() {
        return null;
    }

    @Override
    public List<BannedWord> findAllById(Iterable<String> strings) {
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
    public void delete(BannedWord entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends BannedWord> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<BannedWord> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<BannedWord> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<BannedWord> findByWordContaining(String word, Pageable pageable) {
        return null;
    }
}