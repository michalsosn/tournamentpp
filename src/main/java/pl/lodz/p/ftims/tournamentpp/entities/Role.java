package pl.lodz.p.ftims.tournamentpp.entities;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;

/**
 * @author Michał Sośnicki
 */
public enum Role implements Serializable {
    ROLE_ORGANIZER(OrganizerRoleEntity::new),
    ROLE_SUPPORT(SupportRoleEntity::new),
    ROLE_COMPETITOR(CompetitorRoleEntity::new);

    public static final String ORGANIZER = "ROLE_ORGANIZER";
    public static final String SUPPORT = "ROLE_SUPPORT";
    public static final String COMPETITOR = "ROLE_COMPETITOR";

    private final transient BiFunction<Boolean, AccountEntity, RoleEntity> constructor;

    Role(BiFunction<Boolean, AccountEntity, RoleEntity> constructor) {
        this.constructor = constructor;
    }

    public BiFunction<Boolean, AccountEntity, RoleEntity> getConstructor() {
        return constructor;
    }

    public static Map<Role, RoleEntity> constructAll(
            AccountEntity accountEntity, Collection<Role> roles
    ) {
        Map<Role, RoleEntity> returnMap = new HashMap<>(values().length);
        for (Role role : values()) {
            boolean active = roles != null && roles.contains(role);
            RoleEntity roleEntity = role.getConstructor().apply(active, accountEntity);
            returnMap.put(role, roleEntity);
        }
        return returnMap;
    }

}
