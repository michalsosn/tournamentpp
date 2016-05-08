package pl.lodz.p.ftims.tournamentpp.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.OrganizerRoleEntity;

/**
 * @author Michał Sośnicki
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface OrganizerRoleRepository
        extends CrudRepository<OrganizerRoleEntity, Long> {
}
