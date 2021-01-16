package pl.school.apkaszkolna.repos;


import org.springframework.data.repository.CrudRepository;
import pl.school.apkaszkolna.entities.Uzytkownik;

public interface UzytkownikRepo extends CrudRepository<Uzytkownik, Long> {
}
