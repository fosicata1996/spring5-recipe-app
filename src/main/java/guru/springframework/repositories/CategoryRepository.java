package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);

    default Category findByDescriptionOrThrow(String description) {
        return this.findByDescription(description).orElseThrow(() -> new RuntimeException("Expected Category Not Found"));
    }

}
