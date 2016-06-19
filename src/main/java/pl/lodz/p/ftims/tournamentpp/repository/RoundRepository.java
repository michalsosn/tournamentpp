package pl.lodz.p.ftims.tournamentpp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;

import java.util.Optional;

/**
 * Created by Daniel on 2016-05-14.
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface RoundRepository extends CrudRepository<RoundEntity, Long> {
    Optional<RoundEntity> findById(long id);
}
