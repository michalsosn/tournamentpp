package pl.lodz.p.ftims.tournamentpp.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.*;
import pl.lodz.p.ftims.tournamentpp.repository.*;
import pl.lodz.p.ftims.tournamentpp.trees.Format;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static pl.lodz.p.ftims.tournamentpp.generator.CollectionGenerator.*;

/**
 * @author Michał Sośnicki
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional
public class PersistenceLinker implements GeneratorLinker {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OrganizerRoleRepository organizerRoleRepository;
    @Autowired
    private SupportRoleRepository supportRoleRepository;
    @Autowired
    private CompetitorRoleRepository competitorRoleRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private GameRepository gameRepository;

    @Override
    public Generator<AccountEntity> makeAccount(boolean active, Role... roleTypes) {
        return memorized(accountRepository,
                AccountGenerator.makeAccount(active, roleTypes)
        );
    }

    @Override
    public Generator<GameEntity> makeGame() {
        return memorized(gameRepository,
                getAny(getRounds()).flatMap(GameGenerator::makeGame)
        );
    }

    @Override
    public Generator<RoundEntity> makeRound() {
        return memorized(roundRepository,
                getAny(getTournaments()).flatMap(RoundGenerator::makeRound)
        );
    }

    @Override
    public Generator<TournamentEntity> makeTournament(
            Format format, CompetitorRoleEntity... competitors
    ) {
       return memorized(tournamentRepository, env -> {
           final OrganizerRoleEntity organizer
                   = getAny(getOrganizers()).apply(env);
           return TournamentGenerator
                   .makeTournament(format, organizer, competitors)
                   .apply(env);
       });
    }

    @Override
    public List<AccountEntity> getAccounts() {
        return toList(accountRepository.findAll());
    }

    @Override
    public List<OrganizerRoleEntity> getOrganizers() {
        return toList(organizerRoleRepository.findAll(), OrganizerRoleEntity::isActive);
    }

    @Override
    public List<SupportRoleEntity> getSupporters() {
        return toList(supportRoleRepository.findAll(), SupportRoleEntity::isActive);
    }

    @Override
    public List<CompetitorRoleEntity> getCompetitors() {
        return toList(competitorRoleRepository.findAll(), CompetitorRoleEntity::isActive);
    }

    @Override
    public List<TournamentEntity> getTournaments() {
        return toList(tournamentRepository.findAll());
    }

    @Override
    public List<RoundEntity> getRounds() {
        return toList(roundRepository.findAll());
    }

    @Override
    public List<GameEntity> getGames() {
        return toList(gameRepository.findAll());
    }

    private <T> List<T> toList(Iterable<T> iterable, Predicate<T> predicate) {
        final ArrayList<T> list = new ArrayList<>();
        iterable.forEach(item -> {
            if (predicate.test(item)) {
                list.add(item);
            }
        });
        return list;
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        return toList(iterable, item -> true);
    }

    private <T, I extends Serializable> Generator<T> memorized(
            CrudRepository<T, I> repository, Generator<T> generator
    ) {
        return env -> {
            final T result = generator.apply(env);
            repository.save(result);
            return result;
        };
    }

}
