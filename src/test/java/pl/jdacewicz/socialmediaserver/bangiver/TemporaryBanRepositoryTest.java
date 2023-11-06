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
class TemporaryBanRepositoryTest implements TemporaryBanRepository {

    List<TemporaryBan> database = new LinkedList<>();

    @Override
    public <S extends TemporaryBan> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(e -> {
            database.add(e);
        });
        return (List<S>) database;
    }

    @Override
    public <S extends TemporaryBan> S save(S entity) {
        database.add(entity);
        return entity;
    }

    @Override
    public List<TemporaryBan> findAllByBannedUserAndRevoked(BannedUser bannedUser, boolean revoked) {
        return database.stream()
                .filter(ban -> ban.getBannedUser()
                        .equals(bannedUser))
                .filter(ban -> ban.isRevoked() == revoked)
                .toList();
    }

    @Override
    public List<TemporaryBan> findAllByExpiredAndRevoked(boolean expired, boolean revoked) {
        return database.stream()
                .filter(ban -> ban.isRevoked() == revoked)
                .filter(ban -> ban.isExpired() == expired)
                .toList();
    }

    @Override
    public <S extends TemporaryBan> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends TemporaryBan> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends TemporaryBan> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TemporaryBan> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends TemporaryBan> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends TemporaryBan> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TemporaryBan> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TemporaryBan> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends TemporaryBan, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public Optional<TemporaryBan> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<TemporaryBan> findAll() {
        return null;
    }

    @Override
    public List<TemporaryBan> findAllById(Iterable<String> strings) {
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
    public void delete(TemporaryBan entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends TemporaryBan> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<TemporaryBan> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<TemporaryBan> findAll(Pageable pageable) {
        return null;
    }
}