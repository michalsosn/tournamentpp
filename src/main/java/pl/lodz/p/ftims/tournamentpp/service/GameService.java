package pl.lodz.p.ftims.tournamentpp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.repository.GameRepository;
import pl.lodz.p.ftims.tournamentpp.repository.RoundRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Michał Sośnicki
 */
@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private GameRepository gameRepository;

    public RoundEntity findRound(long roundId) {
        final RoundEntity round = roundRepository.findOne(roundId);
        if (round == null) {
            throw new NoSuchElementException("Round with id = " + roundId + "not found");
        }
        return round;
    }

    public void updateResult(long gameId, Long winnerId) {
        final GameEntity game = gameRepository.findOne(gameId);
        if (game == null) {
            throw new NoSuchElementException("Game with id = " + gameId + " not found");
        }
        if (winnerId == null) {
            game.setWinner(null);
        } else {
            final Optional<CompetitorRoleEntity> winner = game.getCompetitors().stream()
                    .filter(competitor -> competitor.getId() == winnerId)
                    .findAny();
            game.setWinner(winner.get());
        }
    }
}
