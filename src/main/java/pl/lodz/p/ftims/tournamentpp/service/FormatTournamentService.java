package pl.lodz.p.ftims.tournamentpp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
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

    public List<RoundEntity> getRounds(long tournamentId) {
        TournamentEntity tournament = tournamentRepository.findOne(tournamentId);
        List<RoundEntity> rounds = null;
        if (tournament != null) {
            rounds = tournament.getRounds();
        }
        return rounds;
    }
}
