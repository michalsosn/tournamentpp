package pl.lodz.p.ftims.tournamentpp.generator;

import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.entities.RoleEntity;
import pl.lodz.p.ftims.tournamentpp.service.dto.AccountDto;
import pl.lodz.p.ftims.tournamentpp.service.dto.ProfileDto;

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
            account.setEmail(maybeNull(makeEmail()).apply(env));
            account.setBirthdate(maybeNull(makePastDate(70)).apply(env));
            account.setPhone(maybeNull(makeNumeric(9)).apply(env));
            account.setDescription(maybeNull(makeLongString()).apply(env));

            final Map<Role, RoleEntity> allRoles
                    = Role.constructAll(account, Arrays.asList(roleTypes));
            account.getRoles().putAll(allRoles);

            return account;
        };
    }

    public static Generator<AccountDto> makeAccountDto() {
        return makeAccountDto(Role.values());
    }

    public static Generator<AccountDto> makeAccountDto(Role... roleTypes) {
        return env -> {
            final AccountDto accountDto = new AccountDto();
            accountDto.setUsername(env.nextUnique() + makeShortString().apply(env));
            accountDto.setPassword(
                    NumberGenerator.makeInt(4, 25)
                            .flatMap(StringGenerator::makeAlpha)
                            .apply(env)
            );
            accountDto.getRoles().addAll(Arrays.asList(roleTypes));

            accountDto.setName(maybeNull(makeShortString()).apply(env));
            accountDto.setEmail(maybeNull(makeEmail()).apply(env));
            accountDto.setBirthdate(maybeNull(makePastDate(70)).apply(env));
            accountDto.setPhone(maybeNull(makeNumeric(9)).apply(env));
            accountDto.setDescription(maybeNull(makeLongString()).apply(env));

            return accountDto;
        };
    }

    public static Generator<ProfileDto> makeProfileDto(boolean newPassword) {
        return env -> {
            final ProfileDto profileDto = new ProfileDto();

            if (newPassword) {
                profileDto.setPassword(
                        NumberGenerator.makeInt(4, 25)
                                .flatMap(StringGenerator::makeAlpha)
                                .apply(env)
                );
            }

            profileDto.setName(maybeNull(makeShortString()).apply(env));
            profileDto.setEmail(maybeNull(makeShortString()).apply(env));
            profileDto.setBirthdate(maybeNull(makePastDate(70)).apply(env));
            profileDto.setPhone(maybeNull(makeNumeric(9)).apply(env));
            profileDto.setDescription(maybeNull(makeLongString()).apply(env));

            return profileDto;
        };
    }

    private static Generator<String> makeShortString() {
        return NumberGenerator.makeInt(4, 16).flatMap(StringGenerator::makeAlpha);
    }

    private static Generator<String> makeEmail() {
        return makeShortString().map(email ->
                new StringBuilder(email).insert(email.length() / 2, '@').toString()
        );
    }

    private static Generator<String> makeLongString() {
        return NumberGenerator.makeInt(10, 255).flatMap(StringGenerator::makeAlpha);
    }

}
