package pl.lodz.p.ftims.tournamentpp.repository;


import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.OrganizerRoleEntity;

import java.util.Optional;

/**
 * @author Michał Sośnicki
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface OrganizerRoleRepository extends Repository<OrganizerRoleEntity, Long> {
    Iterable<OrganizerRoleEntity> findAll();
    Optional<OrganizerRoleEntity> findByAccountUsername(String username);
}
