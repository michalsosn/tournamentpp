package pl.lodz.p.ftims.tournamentpp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;

/**
 * Created by Daniel on 2016-05-14.
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface GameRepository extends CrudRepository<GameEntity, Long> {
}
