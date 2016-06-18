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
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.OrganizerRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.repository.GameRepository;
import pl.lodz.p.ftims.tournamentpp.repository.OrganizerRoleRepository;
import pl.lodz.p.ftims.tournamentpp.repository.RoundRepository;
import pl.lodz.p.ftims.tournamentpp.repository.TournamentRepository;
import pl.lodz.p.ftims.tournamentpp.trees.TournamentFormat;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @author Michał Sośnicki
 */
@Service
@Transactional
public class TournamentService {

    private final Logger log = LoggerFactory.getLogger(TournamentService.class);

    private static final int PAGE_SIZE = 10;

    @Autowired
    public TournamentService(List<TournamentFormat> tournamentFormats) {
        this.tournamentFormats = tournamentFormats;
    }

    private final List<TournamentFormat> tournamentFormats;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private OrganizerRoleRepository organizerRoleRepository;

    @Autowired
    private RoundRepository roundRepository;

//    @Autowired
//    private CompetitorRoleRepository competitorRoleRepository;

    private final Random random = new SecureRandom();

    @Transactional(readOnly = true)
    public Page<TournamentEntity> listTournaments(int page) {
        Pageable pageRequest = new PageRequest(
                page, PAGE_SIZE, Sort.Direction.DESC, "startTime"
        );
        return tournamentRepository.findAll(pageRequest);
    }


    @Transactional(readOnly = true)
    public Iterable<TournamentEntity> listAllTournaments() {
        return tournamentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Iterable<TournamentEntity> listLastMonthTournaments() {
        return tournamentRepository.getTournamentFromLastMonth();
    }

    @Transactional(readOnly = true)
    public TournamentEntity findTournament(long id) {
        return tournamentRepository.findOne(id);
    }

    public void createTournament(TournamentDto tournament, String username) {
        final TournamentEntity tournamentEntity = new TournamentEntity();
        tournament.applyToEntity(tournamentEntity);
        final OrganizerRoleEntity organizer = organizerRoleRepository
                .findByAccountUsername(username).orElseThrow(() ->
                        new IllegalArgumentException(
                                "Tournament with account username "
                                        + username + " not accessible"));
        tournamentEntity.setOrganizer(organizer);
        tournamentRepository.save(tournamentEntity);
        log.info("Tournament {} created", tournamentEntity.getId());
    }

    public void generateRound(long tournamentId, LocalDateTime endDate) {
        TournamentEntity tournamentEntity = Optional
                .ofNullable(tournamentRepository.findOne(tournamentId))
                .orElseThrow(() ->
                        new IllegalArgumentException("Tournament with ID "
                                + tournamentId + " not accessible"));

        TournamentFormat tournamentFormatter = tournamentFormats.stream()
                .filter(tournamentFormat -> tournamentFormat
                        .supportedFormat(tournamentEntity.getFormat()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Formatter for format "
                                + tournamentEntity.getFormat().name()
                                + " not accessible"));
        RoundEntity roundEntityWithGames =
                tournamentFormatter.prepareRound(tournamentEntity, random);
        RoundEntity roundEntity = new RoundEntity();
        roundEntity.setTournament(tournamentEntity);
        roundEntity.setStartTime(LocalDateTime.now());
        roundEntity.setEndTime(endDate);
        roundEntity = roundRepository.save(roundEntity);
        for (GameEntity g : roundEntityWithGames.getGames()) {
            g.setRound(roundEntity);
            gameRepository.save(g);
            roundEntity.getGames().add(g);
        }
        roundEntity = roundRepository.save(roundEntity);
        tournamentEntity.getRounds().add(roundEntity);
        tournamentRepository.save(tournamentEntity);
    }

}
