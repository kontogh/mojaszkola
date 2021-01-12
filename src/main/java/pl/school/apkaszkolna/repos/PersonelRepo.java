package pl.school.apkaszkolna.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.school.apkaszkolna.entities.Personel;

public interface PersonelRepo extends CrudRepository<Personel, Long> {
    Personel getByUsername(String username);

    @Query(value = "SELECT * FROM PERSONEL WHERE PRZEDMIOT = ?1", nativeQuery = true)
    Personel findByPrzedmiot(String przedmiot);

}
