package pl.lodz.p.ftims.tournamentpp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.repository.RoundRepository;

import java.util.NoSuchElementException;

/**
 * @author Michał Sośnicki
 */
@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private RoundRepository roundRepository;

    public RoundEntity findRound(long roundId) {
        final RoundEntity round = roundRepository.findOne(roundId);
        if (round == null) {
            throw new NoSuchElementException("Round with id " + roundId + "not found");
        }
        return round;
    }

}
