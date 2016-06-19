package pl.lodz.p.ftims.tournamentpp.service.dto;

/**
 * Created by Daniel on 2016-05-14.
 */
public class CompetitorDto {
    private String name;
    private long id;

    public CompetitorDto(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
