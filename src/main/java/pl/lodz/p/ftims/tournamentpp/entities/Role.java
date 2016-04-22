package pl.lodz.p.ftims.tournamentpp.entities;

import java.util.function.BiFunction;

/**
 * @author Michał Sośnicki
 */
public enum Role {
    ROLE_ORGANIZER(OrganizerRoleEntity::new),
    ROLE_SUPPORT(SupportRoleEntity::new),
    ROLE_COMPETITOR(CompetitorRoleEntity::new);

    public static final String ORGANIZER = "ROLE_ORGANIZER";
    public static final String SUPPORT = "ROLE_SUPPORT";
    public static final String COMPETITOR = "ROLE_COMPETITOR";

    private final BiFunction<Boolean, AccountEntity, RoleEntity> constructor;

    Role(BiFunction<Boolean, AccountEntity, RoleEntity> constructor) {
        this.constructor = constructor;
    }

    public BiFunction<Boolean, AccountEntity, RoleEntity> getConstructor() {
        return constructor;
    }
}
