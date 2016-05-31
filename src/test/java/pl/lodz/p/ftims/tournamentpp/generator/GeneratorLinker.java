package pl.lodz.p.ftims.tournamentpp.generator;

import pl.lodz.p.ftims.tournamentpp.entities.*;
import pl.lodz.p.ftims.tournamentpp.trees.Format;

import java.util.List;

/**
 * @author Michał Sośnicki
 */
public interface GeneratorLinker {

    Generator<AccountEntity> makeAccount(boolean active, Role... roleTypes);
    Generator<GameEntity> makeGame();
    Generator<RoundEntity> makeRound();
    Generator<TournamentEntity> makeTournament(
            Format format, CompetitorRoleEntity... competitors
    );

    List<AccountEntity> getAccounts();
    List<OrganizerRoleEntity> getOrganizers();
    List<SupportRoleEntity> getSupporters();
    List<CompetitorRoleEntity> getCompetitors();
    List<TournamentEntity> getTournaments();
    List<RoundEntity> getRounds();
    List<GameEntity> getGames();

}
