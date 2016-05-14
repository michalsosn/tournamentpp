package pl.lodz.p.ftims.tournamentpp.trees;

import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;

/**
 * Created by Daniel on 2016-05-09.
 */
public interface TournamentFormat {
    RoundEntity prepareRound(TournamentEntity tournament);
}
