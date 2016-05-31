package pl.lodz.p.ftims.tournamentpp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.repository.RoundRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class RoundEntityService {
    @Autowired
    private RoundRepository roundRepository;

    public void addRound(RoundEntity roundEntity) {
        roundRepository.save(roundEntity);
    }

    public RoundEntity updateRound(RoundEntity round) {
        if (!roundRepository.exists(
                Optional.ofNullable(round)
                        .map(RoundEntity::getId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Round ID of round "
                                        + round + " not accessible")))) {
            throw new IllegalArgumentException("Round with ID "
                    + round.getId() + " not found");
        }
        return roundRepository.save(round);
    }

    public RoundEntity startRound(long roundId, LocalDateTime dateTime) {
        if (!roundRepository.exists(roundId)) {
            throw new IllegalArgumentException("Round with ID "
                    + roundId + " not found");
        }
        RoundEntity roundEntity = roundRepository.findOne(roundId);
        roundEntity.setStartTime(dateTime);
        return updateRound(roundEntity);
    }

    public RoundEntity startRound(long roundId) {
        return startRound(roundId, LocalDateTime.now());
    }

    public RoundEntity endRound(long roundId, LocalDateTime dateTime) {
        if (!roundRepository.exists(roundId)) {
            throw new IllegalArgumentException("Round with ID "
                    + roundId + " not found");
        }
        RoundEntity roundEntity = roundRepository.findOne(roundId);
        roundEntity.setEndTime(dateTime);
        return updateRound(roundEntity);
    }

    public RoundEntity endRound(long roundId) {
        return endRound(roundId, LocalDateTime.now());
    }
}
