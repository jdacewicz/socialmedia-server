package pl.jdacewicz.socialmediaserver.bangiver;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import pl.jdacewicz.socialmediaserver.bangiver.dto.BannedUser;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("all")
class BanRepositoryTest implements BanRepository {

    List<Ban> database = new LinkedList<>();

    public List<Ban> findAllNotRevokedByBannedUserId(String bannedUserId) {
        return database.stream()
                .filter(ban -> ban.getBannedUser()
                        .userId()
                        .equals(bannedUserId))
                .filter(ban -> ban.isRevoked() == false)
                .toList();
    }

    @Override
    public <S extends Ban> S save(S entity) {
        database.add(entity);
        return entity;
    }

    @Override
    public <S extends Ban> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(e -> {
            database.add(e);
        });
        return (List<S>) database;
    }

    @Override
    public List<Ban> findAllByBannedUserAndRevoked(BannedUser bannedUser, boolean revoked) {
        return database.stream()
                .filter(ban -> ban.getBannedUser()
                        .equals(bannedUser))
                .filter(ban -> ban.isRevoked() == revoked)
                .toList();
    }

    @Override
    public boolean existsByBannedUserAndRevoked(BannedUser bannedUser, boolean revoked) {
        return database.stream()
                .anyMatch(ban -> ban.getBannedUser().equals(bannedUser) &&
                        ban.isRevoked() == revoked);
    }

    @Override
    public <S extends Ban> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Ban> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Ban> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Ban> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Ban> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Ban> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Ban> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Ban> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Ban, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public Optional<Ban> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Ban> findAll() {
        return null;
    }

    @Override
    public List<Ban> findAllById(Iterable<String> strings) {
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
    public void delete(Ban entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Ban> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Ban> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Ban> findAll(Pageable pageable) {
        return null;
    }
}