package pl.lodz.p.ftims.tournamentpp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.repository.TournamentRepository;

@Transactional
@Service
public class TournamentService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private TournamentRepository tournamentRepository;

    public void createTournament(TournamentDto tournament) {
        TournamentEntity tournamentEntity = new TournamentEntity();
        tournament.applyToEntity(tournamentEntity);
        tournamentRepository.save(tournamentEntity);
        log.info("Tournament {} created", tournamentEntity.getId());
    }

}
