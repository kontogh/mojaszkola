package pl.school.apkaszkolna.repos;

import org.springframework.data.repository.CrudRepository;
import pl.school.apkaszkolna.entities.Klasa;

public interface KlasaRepo extends CrudRepository<Klasa, String> {
}
