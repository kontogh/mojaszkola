package pl.school.apkaszkolna.repos;

import org.springframework.data.repository.CrudRepository;
import pl.school.apkaszkolna.entities.WydarzeniaPozalekcyne;

public interface WydarzeniaRepo extends CrudRepository<WydarzeniaPozalekcyne, Long> {
}
