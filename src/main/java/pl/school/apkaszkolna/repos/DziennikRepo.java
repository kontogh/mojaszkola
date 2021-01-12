package pl.school.apkaszkolna.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.school.apkaszkolna.entities.Dziennik;

import java.util.List;

public interface DziennikRepo extends CrudRepository<Dziennik, Long> {
    @Query(value = "SELECT IMIE FROM UCZEN INNER JOIN DZIENNIK ON UCZEN.ID = DZIENNIK.ID WHERE PRZEDMIOT = ?2 AND ID_KLASY = ?1", nativeQuery = true)
    List<String> imiona(String klasa, String przedmiot);
    @Query(value = "SELECT NAZWISKO FROM UCZEN INNER JOIN DZIENNIK ON UCZEN.ID = DZIENNIK.ID WHERE PRZEDMIOT = ?2 AND ID_KLASY = ?1", nativeQuery = true)
    List<String> nazwiska(String klasa, String przedmiot);
    @Query(value = "SELECT OCENY FROM UCZEN INNER JOIN DZIENNIK ON UCZEN.ID = DZIENNIK.ID WHERE PRZEDMIOT = ?2 AND ID_KLASY = ?1", nativeQuery = true)
    List<String> oceny(String klasa, String przedmiot);
    @Query(value = "SELECT * FROM DZIENNIK WHERE ID =?1 AND PRZEDMIOT = ?2", nativeQuery = true)
    Dziennik dziennikucznia(long idUcznia, String przedmiot);
    @Query(value = "SELECT * FROM DZIENNIK WHERE ID = ?1", nativeQuery = true)
    List<Dziennik> ocenyucznia(long idUcznia);


}
