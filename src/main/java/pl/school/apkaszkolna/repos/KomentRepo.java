package pl.school.apkaszkolna.repos;

import org.springframework.data.repository.CrudRepository;
import pl.school.apkaszkolna.entities.Comments;

public interface KomentRepo extends CrudRepository<Comments, Long> {
}
