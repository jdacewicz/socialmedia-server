package pl.jdacewicz.socialmediaserver.datagrouper;

import org.springframework.data.mongodb.repository.MongoRepository;

interface PostGroupRepository extends MongoRepository<PostGroup, String> {
}
