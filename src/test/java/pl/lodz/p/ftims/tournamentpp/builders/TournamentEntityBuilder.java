package pl.lodz.p.ftims.tournamentpp.builders;

import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.OrganizerRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class TournamentEntityBuilder {
    private String location;
    private String description;
    private LocalDateTime startTime;
    private OrganizerRoleEntity organizer;
    private List<CompetitorRoleEntity> competitors = new ArrayList<>();
    private List<RoundEntity> rounds = new ArrayList<>();

    private TournamentEntityBuilder() {
    }

    public static TournamentEntityBuilder aTournamentEntity() {
        return new TournamentEntityBuilder();
    }

    public TournamentEntityBuilder withLocation(String location) {
        this.location = location;
        return this;
    }

    public TournamentEntityBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TournamentEntityBuilder withStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public TournamentEntityBuilder withOrganizer(OrganizerRoleEntity organizer) {
        this.organizer = organizer;
        return this;
    }

    public TournamentEntityBuilder withCompetitor(CompetitorRoleEntity competitor) {
        this.competitors.add(competitor);
        return this;
    }

    public TournamentEntityBuilder withCompetitors(
            Collection<CompetitorRoleEntity> competitors) {
        this.competitors.addAll(competitors);
        return this;
    }

    public TournamentEntityBuilder withRound(RoundEntity round) {
        this.rounds.add(round);
        return this;
    }

    public TournamentEntityBuilder withRounds(Collection<RoundEntity> rounds) {
        this.rounds.addAll(rounds);
        return this;
    }

    public TournamentEntity build() {
        TournamentEntity tournamentEntity = new TournamentEntity();
        tournamentEntity.setLocation(location);
        tournamentEntity.setDescription(description);
        tournamentEntity.setStartTime(startTime);
        tournamentEntity.setOrganizer(organizer);
        tournamentEntity.getCompetitors().addAll(competitors);
        tournamentEntity.getRounds().addAll(rounds);
        return tournamentEntity;
    }
}
