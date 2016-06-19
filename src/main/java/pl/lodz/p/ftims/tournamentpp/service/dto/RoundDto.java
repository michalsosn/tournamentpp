package pl.lodz.p.ftims.tournamentpp.service.dto;

import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa, z której dane przejdą do formatki przy pokazywaniu drzewka
 * Created by Daniel on 2016-05-14.
 */
public class RoundDto {
    private List<GameDto> games;
    private String round;

    private LocalDateTime start;

    public RoundDto(List<GameEntity> games, String round, LocalDateTime start) {
        this.round = round;
        this.start = start;

        this.games = new ArrayList<>();
        for (GameEntity g : games) {
            GameDto game = new GameDto(g.getCompetitors());
            game.setWinner(g.getWinner());
            this.games.add(game);
        }
    }

    public List<GameDto> getGames() {
        return games;
    }

    public String getRound() {
        return round;
    }

    public LocalDateTime getStart() {
        return start;
    }
}
