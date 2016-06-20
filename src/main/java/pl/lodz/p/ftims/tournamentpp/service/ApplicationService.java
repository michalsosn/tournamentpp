package pl.lodz.p.ftims.tournamentpp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.ApplicationEntity;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.repository.ApplicationRepository;
import pl.lodz.p.ftims.tournamentpp.repository.CompetitorRoleRepository;
import pl.lodz.p.ftims.tournamentpp.repository.TournamentRepository;
import pl.lodz.p.ftims.tournamentpp.service.dto.ApplicationDto;
import pl.lodz.p.ftims.tournamentpp.util.EntityUtils;

@Transactional
@Service
public class ApplicationService {
    @Autowired
    private CompetitorRoleRepository competitorRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    public TournamentEntity findApplications(long tournamentId) {
        final TournamentEntity tournament = tournamentRepository.findOne(tournamentId);
        EntityUtils.loadEager(tournament.getApplications());
        return tournament;
    }

    public void saveApplication(
            long tournamentId, String competitorName, ApplicationDto applicationDto
    ) {
        TournamentEntity tournament = tournamentRepository.findById(tournamentId).get();
        CompetitorRoleEntity competitor
                = competitorRepository.findByAccountUsername(competitorName).get();

        if (!tournament.getRounds().isEmpty()) {
            throw new IllegalArgumentException("Tournament has already started");
        }

        ApplicationEntity application = new ApplicationEntity(
                null, tournament, competitor
        );
        applicationDto.applyToEntity(application);

        applicationRepository.save(application);
    }

    public void acceptApplication(long applicationId) {
        final ApplicationEntity application
                = applicationRepository.findById(applicationId).get();
        final CompetitorRoleEntity competitor = application.getCompetitor();
        final TournamentEntity tournament = application.getTournament();
        tournament.getCompetitors().add(competitor);
        applicationRepository.delete(application);
    }

    public static class TournamentApplicationDtoPair {
        private final TournamentEntity tournament;
        private final ApplicationDto applicationDto;

        public TournamentApplicationDtoPair(TournamentEntity tournament,
                                            ApplicationDto applicationDto) {
            this.tournament = tournament;
            this.applicationDto = applicationDto;
        }

        public TournamentEntity getTournament() {
            return tournament;
        }

        public ApplicationDto getApplicationDto() {
            return applicationDto;
        }
    }
}
