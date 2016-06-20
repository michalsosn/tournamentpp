package pl.lodz.p.ftims.tournamentpp.service.dto;

import pl.lodz.p.ftims.tournamentpp.entities.ApplicationEntity;

/**
 * @author Michał Sośnicki
 */
public class ApplicationDto {

    private String message;

    public ApplicationDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void applyToEntity(ApplicationEntity applicationEntity) {
        applicationEntity.setMessage(message);
    }

}
