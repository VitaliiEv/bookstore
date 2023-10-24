package vitaliiev.bookstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import vitaliiev.bookstore.entity.Author;

import java.util.UUID;

@RepositoryRestResource
public interface AuthorRepository extends CrudRepository<Author, UUID> {
}
