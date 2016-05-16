package pl.lodz.p.ftims.tournamentpp.generator;

import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.entities.RoleEntity;

import java.util.Arrays;
import java.util.Map;

import static pl.lodz.p.ftims.tournamentpp.generator.BooleanGenerator.maybeNull;
import static pl.lodz.p.ftims.tournamentpp.generator.StringGenerator.makeNumeric;
import static pl.lodz.p.ftims.tournamentpp.generator.TimeGenerator.makePastDate;

/**
 * @author Michał Sośnicki
 */
public final class AccountGenerator {

    public static Generator<AccountEntity> makeAccount() {
        return makeAccount(true, Role.values());
    }

    public static Generator<AccountEntity> makeAccount(
            boolean active, Role... roleTypes
    ) {
        return env -> {
            final AccountEntity account = new AccountEntity();
            account.setUsername(env.nextUnique() + makeShortString().apply(env));
            account.setPassword(
                    NumberGenerator.makeInt(4, 25)
                            .flatMap(StringGenerator::makeAlpha)
                            .map(env.getPasswordEncoder()::encode)
                            .apply(env)
            );
            account.setActive(active);

            account.setName(maybeNull(makeShortString()).apply(env));
            account.setEmail(maybeNull(makeShortString()).apply(env));
            account.setBirthdate(maybeNull(makePastDate(70)).apply(env));
            account.setPhone(maybeNull(makeNumeric(9)).apply(env));
            account.setDescription(maybeNull(makeLongString()).apply(env));

            final Map<Role, RoleEntity> allRoles
                    = Role.constructAll(account, Arrays.asList(roleTypes));
            account.getRoles().putAll(allRoles);

            return account;
        };
    }

    private static Generator<String> makeShortString() {
        return NumberGenerator.makeInt(4, 16).flatMap(StringGenerator::makeAlpha);
    }

    private static Generator<String> makeLongString() {
        return NumberGenerator.makeInt(10, 255).flatMap(StringGenerator::makeAlpha);
    }

}
