package pl.lodz.p.ftims.tournamentpp.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;

import java.util.Optional;

/**
 * @author Michał Sośnicki
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface AccountRepository extends
        PagingAndSortingRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUsername(String username);

    @Query("SELECT t FROM Account t WHERE "
            +
            "LOWER(t.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR "
            +
            "LOWER(t.username) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    Page<AccountEntity> findBySearchTerm(@Param("searchTerm") String searchTerm,
                                         Pageable pageRequest);

}
