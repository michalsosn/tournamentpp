package pl.lodz.p.ftims.tournamentpp.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;

/**
 * @author Michał Sośnicki
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface TournamentRepository extends PagingAndSortingRepository<TournamentEntity, Long> {
}
