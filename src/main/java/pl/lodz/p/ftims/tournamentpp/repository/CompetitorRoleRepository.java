package pl.lodz.p.ftims.tournamentpp.repository;


import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;

/**
 * @author Michał Sośnicki
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface CompetitorRoleRepository extends Repository<CompetitorRoleEntity, Long> {
}
