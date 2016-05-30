package pl.lodz.p.ftims.tournamentpp.service;

import org.springframework.format.annotation.DateTimeFormat;
import pl.lodz.p.ftims.tournamentpp.entities.OrganizerRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.trees.Format;

import java.time.LocalDateTime;

public class TournamentDto {

    private String location;

    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    private Format format;

    private OrganizerRoleEntity organizer;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public OrganizerRoleEntity getOrganizer() {
        return organizer;
    }

    public void setOrganizer(OrganizerRoleEntity organizer) {
        this.organizer = organizer;
    }

    public void applyToEntity(TournamentEntity tournamentEntity) {
        tournamentEntity.setLocation(location);
        tournamentEntity.setDescription(description);
        tournamentEntity.setStartTime(startTime);
        tournamentEntity.setFormat(format);
        tournamentEntity.setOrganizer(organizer);
    }

}
