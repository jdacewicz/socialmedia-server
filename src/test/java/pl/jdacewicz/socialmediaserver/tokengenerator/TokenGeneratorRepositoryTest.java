package pl.jdacewicz.socialmediaserver.tokengenerator;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@SuppressWarnings("all")
class TokenGeneratorRepositoryTest implements TokenGeneratorRepository {

    Map<String, Token> database = new ConcurrentHashMap<>();

    @Override
    public Optional<Token> findByCode(String code) {
        return database.values()
                .stream()
                .filter(token -> token.code().equals(code))
                .findFirst();
    }

    @Override
    public List<Token> findByUserId(String userId) {
        return database.values()
                .stream()
                .filter(token -> token.userId().equals(userId))
                .toList();
    }

    @Override
    public Token save(Token entity) {
        database.put(entity.code(), entity);
        return entity;
    }

    @Override
    public <S extends Token> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(e -> {
            database.put(e.code(), e);
        });
        return (List) entities;
    }

    @Override
    public <S extends Token> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Token> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Token> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Token> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Token> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Token> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Token> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Token> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Token, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public Optional<Token> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Token> findAll() {
        return null;
    }

    @Override
    public List<Token> findAllById(Iterable<String> strings) {
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
    public void delete(Token entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Token> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Token> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Token> findAll(Pageable pageable) {
        return null;
    }
}
