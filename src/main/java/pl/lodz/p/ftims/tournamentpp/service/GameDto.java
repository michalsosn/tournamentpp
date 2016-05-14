package pl.lodz.p.ftims.tournamentpp.service;

import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 2016-05-14.
 */
public class GameDto {
    private CompetitorDto winner;
    private List<CompetitorDto> competitors;

    public GameDto(List<CompetitorRoleEntity> competitors) {
        this.competitors = new ArrayList<>();
        for (CompetitorRoleEntity entity : competitors) {
            this.competitors.add(
                    new CompetitorDto(entity.getId(),entity.getAccount().getName()));
        }
    }

    public List<CompetitorDto> getCompetitors() {
        return competitors;
    }

    public CompetitorDto getWinner() {
        return winner;
    }

    public void setWinner(CompetitorRoleEntity winner) {
        this.winner = new CompetitorDto(winner.getId(), winner.getAccount().getName());
    }
}
