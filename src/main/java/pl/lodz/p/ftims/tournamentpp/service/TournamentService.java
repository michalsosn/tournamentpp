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

    public void deleteTournament(Long id) {
        tournamentRepository.delete(id);
        log.info("Tournament id:{} created", id);
    }

    public void updateTournament(TournamentDto tournament, Long tournamentId) {
        TournamentEntity tournamentEntity = tournamentRepository.findOne(tournamentId);
        tournament.applyToEntity(tournamentEntity);
        tournamentRepository.save(tournamentEntity);
        log.info("Tournament {} edited", tournamentEntity.getId());
    }

    public TournamentDto getTournamentDto(Long id) {
        TournamentDto tournamentDto = new TournamentDto();
        TournamentEntity tournamentEntity = tournamentRepository.findOne(id);

        tournamentDto.setDescription(tournamentEntity.getDescription());
        tournamentDto.setFormat(tournamentEntity.getFormat());
        tournamentDto.setLocation(tournamentEntity.getLocation());
        tournamentDto.setOrganizer(tournamentEntity.getOrganizer());
        tournamentDto.setStartTime(tournamentEntity.getStartTime());

        return tournamentDto;
    }

}
