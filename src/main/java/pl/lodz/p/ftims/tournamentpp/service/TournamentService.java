package pl.lodz.p.ftims.tournamentpp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.repository.TournamentRepository;

/**
 * @author Michał Sośnicki
 */
@Service
@Transactional
public class TournamentService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    private static final int PAGE_SIZE = 10;

    @Autowired
    private TournamentRepository tournamentRepository;

    public Page<TournamentEntity> listTournaments(int page) {
        Pageable pageRequest = new PageRequest(
                page, PAGE_SIZE, Sort.Direction.DESC, "startTime"
        );
        return tournamentRepository.findAll(pageRequest);
    }

    public TournamentEntity findTournament(long id) {
        return tournamentRepository.findOne(id);
    }

	public void createTournament(TournamentDto tournament) {
		TournamentEntity tournamentEntity = new TournamentEntity();
		tournament.applyToEntity(tournamentEntity);
		tournamentRepository.save(tournamentEntity);
		 log.info("Tournament {} created", tournamentEntity.getId());
	}
	
}
