package pl.school.apkaszkolna.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.school.apkaszkolna.entities.Rodzic;

import java.util.Optional;

public interface RodzicRepo extends CrudRepository<Rodzic, Long> {
    Rodzic getByUsername(String username);

    /**
     *
     * @param id rodzica pobrane od konretnego ucznia
     * @return rodzic konretnefo ucznia
     */
    @Query(value = "SELECT * FROM RODZIC WHERE ID = ?1", nativeQuery = true)
    Optional<Rodzic> getByUczenId(long id);

}
