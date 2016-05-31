package pl.lodz.p.ftims.tournamentpp.repository;


import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;

import java.util.Optional;

/**
 * @author Michał Sośnicki
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface CompetitorRoleRepository extends Repository<CompetitorRoleEntity, Long> {
    Iterable<CompetitorRoleEntity> findAll();
    Optional<CompetitorRoleEntity> findByAccountUsername(String username);
}
