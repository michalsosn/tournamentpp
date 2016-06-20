package pl.lodz.p.ftims.tournamentpp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.ApplicationEntity;

import java.util.Optional;

@Transactional(propagation = Propagation.MANDATORY)
public interface ApplicationRepository extends CrudRepository<ApplicationEntity, Long> {
    Optional<ApplicationEntity> findById(long id);
}
