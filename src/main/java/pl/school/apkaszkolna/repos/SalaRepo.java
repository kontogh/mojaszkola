package pl.school.apkaszkolna.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.school.apkaszkolna.entities.Sala;
import pl.school.apkaszkolna.guis.komponenty.Przedmioty;

public interface SalaRepo extends CrudRepository<Sala, Long> {

    @Query(value = "SELECT * FROM SALA WHERE PRZEDMIOT = ?1", nativeQuery = true)
    Sala findbyPrzedmiot(String przedmioty);

}
