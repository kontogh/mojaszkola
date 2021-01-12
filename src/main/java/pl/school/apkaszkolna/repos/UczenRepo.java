package pl.school.apkaszkolna.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.school.apkaszkolna.entities.Uczen;

import java.util.List;

public interface UczenRepo extends CrudRepository<Uczen, Long> {
    Uczen getByUsername(String username);

    @Query(value = "SELECT * FROM UCZEN WHERE IMIE =?1 and NAZWISKO =?2 and ID_KLASY =?3 ", nativeQuery = true)
    Uczen getUczen(String imie, String Nazwisko, String Klasa);
    @Query(value = "SELECT * FROM UCZEN WHERE ID_KLASY =?1 ", nativeQuery = true)
    List<Uczen> getKlasa(String Klasa);
    @Query(value = "SELECT * FROM UCZEN WHERE RODZIC_ID = ?1", nativeQuery = true)
    List <Uczen> dziecirodzica(long id);

}
