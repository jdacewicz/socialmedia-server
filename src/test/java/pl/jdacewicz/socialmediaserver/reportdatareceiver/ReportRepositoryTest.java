package pl.jdacewicz.socialmediaserver.reportdatareceiver;

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
class ReportRepositoryTest implements ReportRepository {

    List<Report> database = new LinkedList<>();

    @Override
    public List<Report> findAll() {
        return database;
    }

    @Override
    public List<Report> findAllByDataType(DataType dataType) {
        return null;
    }

    @Override
    public <S extends Report> S save(S entity) {
        database.add(entity);
        return entity;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public <S extends Report> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Report> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Report> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Report> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Report> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Report> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Report> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Report> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Report, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Report> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Report> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Report> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Report entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Report> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Report> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Report> findAll(Pageable pageable) {
        return null;
    }
}
