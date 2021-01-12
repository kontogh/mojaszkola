package pl.school.apkaszkolna.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.school.apkaszkolna.entities.PlanLekcji;
import pl.school.apkaszkolna.guis.komponenty.Dni;

import java.util.List;

public interface PlanLekcjiRepo extends CrudRepository<PlanLekcji, Long> {

    @Query(value = "SELECT * FROM PLAN_LEKCJI WHERE ID_KLASY = ?1 AND DNI = ?2", nativeQuery = true)
    List<PlanLekcji> getByidKlasy(String idKlasy, String dni);
    @Query(value = "SELECT * FROM PLAN_LEKCJI WHERE NAUCZYCIEL_ID = ?1 AND DNI = ?2", nativeQuery = true)
    List<PlanLekcji> getByidTeacher(long idTeacher, String dni);

}
