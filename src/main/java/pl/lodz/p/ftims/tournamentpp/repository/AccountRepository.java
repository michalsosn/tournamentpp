package pl.lodz.p.ftims.tournamentpp.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;

import java.util.Optional;

/**
 * @author Michał Sośnicki
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUsername(String username);
}