package pl.lodz.p.ftims.tournamentpp.builders;

import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class RoundEntityBuilder {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TournamentEntity tournament;
    private final List<GameEntity> games = new ArrayList<>();

    private RoundEntityBuilder() {
    }

    public static RoundEntityBuilder aRoundEntity() {
        return new RoundEntityBuilder();
    }

    public RoundEntityBuilder withStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public RoundEntityBuilder withEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public RoundEntityBuilder withTournament(TournamentEntity tournament) {
        this.tournament = tournament;
        return this;
    }

    public RoundEntityBuilder withGame(GameEntity game) {
        this.games.add(game);
        return this;
    }

    public RoundEntityBuilder withGames(Collection<GameEntity> games) {
        this.games.addAll(games);
        return this;
    }

    public RoundEntity build() {
        RoundEntity roundEntity = new RoundEntity();
        roundEntity.setStartTime(startTime);
        roundEntity.setEndTime(endTime);
        roundEntity.setTournament(tournament);
        roundEntity.getGames().addAll(games);
        return roundEntity;
    }
}
