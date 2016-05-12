package pl.lodz.p.ftims.tournamentpp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.repository.TournamentRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Daniel on 2016-05-11.
 */
@Transactional
@Service
public class FormatTournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    public List<RoundEntity> getRounds(Long tournamentId) {
        checkIfTournamentExists(tournamentId);
        List<RoundEntity> rounds = tournamentRepository
                .findById(tournamentId).get().getRounds();
        return rounds;
    }

    private void checkIfTournamentExists(Long tournamentId) {
        if (!tournamentRepository.findById(tournamentId).isPresent()) {
            throw new IllegalArgumentException(
                    "Tournament " + tournamentId + " not exists");
        }
    }

}
