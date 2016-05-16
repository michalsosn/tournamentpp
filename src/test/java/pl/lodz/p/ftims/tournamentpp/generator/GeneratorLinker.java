package pl.lodz.p.ftims.tournamentpp.generator;

import pl.lodz.p.ftims.tournamentpp.entities.*;
import pl.lodz.p.ftims.tournamentpp.trees.Format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Michał Sośnicki
 */
public class GeneratorLinker {

    private final List<AccountEntity> accounts = new ArrayList<>();
    private final List<OrganizerRoleEntity> organizers = new ArrayList<>();
    private final List<SupportRoleEntity> supporters = new ArrayList<>();
    private final List<CompetitorRoleEntity> competitors = new ArrayList<>();
    private final List<TournamentEntity> tournaments = new ArrayList<>();
    private final List<RoundEntity> rounds = new ArrayList<>();
    private final List<GameEntity> games = new ArrayList<>();

    public Generator<AccountEntity> makeAccount(boolean active, Role... roleTypes) {
        return env -> {
            final AccountEntity account
                    = AccountGenerator.makeAccount(active, roleTypes).apply(env);
            accounts.add(account);
            account.getRoles().forEach((type, role) -> {
                switch (type) {
                    case ROLE_ORGANIZER:
                        organizers.add((OrganizerRoleEntity) role);
                        break;
                    case ROLE_SUPPORT:
                        supporters.add((SupportRoleEntity) role);
                        break;
                    case ROLE_COMPETITOR:
                        competitors.add((CompetitorRoleEntity) role);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected role " + type);
                }
            });
            return account;
        };
    }

    public Generator<GameEntity> makeGame() {
        return memorized(games, env ->{
            final RoundEntity round = CollectionGenerator.getAny(rounds).apply(env);
            return GameGenerator.makeGame(round).apply(env);
        });
    }

    public Generator<RoundEntity> makeRound() {
        return memorized(rounds, env -> {
            final TournamentEntity tournament
                    = CollectionGenerator.getAny(tournaments).apply(env);
            return RoundGenerator.makeRound(tournament).apply(env);
        });
    }

    public Generator<TournamentEntity> makeTournament(
            Format format, CompetitorRoleEntity... competitors
    ) {
       return memorized(tournaments, env -> {
           final OrganizerRoleEntity organizer
                   = CollectionGenerator.getAny(organizers).apply(env);
           return TournamentGenerator
                   .makeTournament(format, organizer, competitors)
                   .apply(env);
       });
    }

    public List<AccountEntity> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public List<OrganizerRoleEntity> getOrganizers() {
        return Collections.unmodifiableList(organizers.stream()
                .filter(OrganizerRoleEntity::isActive)
                .collect(Collectors.toList())
        );
    }

    public List<SupportRoleEntity> getSupporters() {
        return Collections.unmodifiableList(supporters.stream()
                .filter(SupportRoleEntity::isActive)
                .collect(Collectors.toList())
        );
    }

    public List<CompetitorRoleEntity> getCompetitors() {
        return Collections.unmodifiableList(competitors.stream()
                .filter(CompetitorRoleEntity::isActive)
                .collect(Collectors.toList())
        );
    }

    public List<TournamentEntity> getTournaments() {
        return Collections.unmodifiableList(tournaments);
    }

    public List<RoundEntity> getRounds() {
        return Collections.unmodifiableList(rounds);
    }

    public List<GameEntity> getGames() {
        return Collections.unmodifiableList(games);
    }

    private <T> Generator<T> memorized(Collection<T> collection, Generator<T> generator) {
        return env -> {
            final T result = generator.apply(env);
            collection.add(result);
            return result;
        };
    }

}
