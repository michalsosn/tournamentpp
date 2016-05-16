package pl.lodz.p.ftims.tournamentpp.generator;

import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;

/**
 * @author Michał Sośnicki
 */
public final class GameGenerator {

    private GameGenerator() {
    }

    public static Generator<GameEntity> makeGame(RoundEntity round) {
        return env -> {
            final GameEntity game = new GameEntity(round);
            round.getGames().add(game);
            return game;
        };
    }

}
