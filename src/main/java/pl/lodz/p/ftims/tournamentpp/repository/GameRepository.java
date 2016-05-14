package pl.lodz.p.ftims.tournamentpp.repository;

import org.springframework.data.repository.CrudRepository;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;

/**
 * Created by Daniel on 2016-05-14.
 */
public interface GameRepository extends CrudRepository<GameEntity, Long> {
}
