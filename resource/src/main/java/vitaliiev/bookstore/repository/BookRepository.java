package vitaliiev.bookstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import vitaliiev.bookstore.entity.Book;

import java.util.UUID;

@RepositoryRestResource
public interface BookRepository extends CrudRepository<Book, UUID> {
}
