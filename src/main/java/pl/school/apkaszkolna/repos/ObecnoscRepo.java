package pl.school.apkaszkolna.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.school.apkaszkolna.entities.Obecnosc;

import java.util.List;

public interface ObecnoscRepo extends CrudRepository<Obecnosc, Long> {

    @Query(value = "SELECT * FROM OBECNOSC WHERE UCZEN_ID = ?1", nativeQuery = true)
    List<Obecnosc> getforStudId(long id);
}
