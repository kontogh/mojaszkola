package pl.school.apkaszkolna.repos;


import org.springframework.data.repository.CrudRepository;
import pl.school.apkaszkolna.entities.Uzytkownik;

//byc moze to ususnąc
public interface UzytkownikRepo extends CrudRepository<Uzytkownik, Long> {
}
