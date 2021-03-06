package pl.lodz.p.ftims.tournamentpp.repository;


import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.SupportRoleEntity;

import java.util.Optional;

/**
 * @author Michał Sośnicki
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface SupportRoleRepository extends Repository<SupportRoleEntity, Long> {
    Iterable<SupportRoleEntity> findAll();
    Optional<SupportRoleEntity> findByAccountUsername(String username);
}
