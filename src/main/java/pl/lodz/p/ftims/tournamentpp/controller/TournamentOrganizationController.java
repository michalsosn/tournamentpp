package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ftims.tournamentpp.entities.*;
import pl.lodz.p.ftims.tournamentpp.service.TournamentService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Michał Sośnicki
 */
@Controller
public class TournamentOrganizationController {

    @Autowired
    private TournamentService tournamentService;

    // TODO to może być przydatne do wyświetlenia listy turniejów!
    @RequestMapping(path = "/tournament/tournaments", method = RequestMethod.GET)
    public String listTournaments(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            Model model
    ) {
        final Page<TournamentEntity> tournaments
                = tournamentService.listTournaments(page);
        model.addAttribute("tournaments", tournaments);
        return "/tournament/tournaments";
    }

    @RequestMapping(path = "/tournament/tournament/{id}", method = RequestMethod.GET)
    public String getTournament(@PathVariable long id, Model model) {
        final TournamentEntity tournament = tournamentService.findTournament(id);
        model.addAttribute("tournament", tournament);
        model.addAttribute("roundParams", new RoundParamsPlaceholder());
        return "/tournament/tournament";
    }

    @RequestMapping(path = "/tournament/tournament/{id}/round",
            method = RequestMethod.POST)
    public String addNewRound(@PathVariable long id,
                              @Valid @ModelAttribute("roundParams")
                                      RoundParamsPlaceholder roundParams) {
        tournamentService.generateRound(id, roundParams.getEndDate());
        return "redirect:/tournament/tournament/" + id;
    }

    @RequestMapping(path = "/tournament/tournament/{id}/round/{roundId}",
                           method = RequestMethod.GET)
    public String showRoundScore(@PathVariable long id,
                                 @PathVariable long roundId,
                                 Model model) {
        List<GameCompetitor> gameCompetitor = new ArrayList<>();
        final TournamentEntity tournament = tournamentService.findTournament(id);
        tournament.getRounds().stream()
                .filter(roundEntity -> roundEntity.getId().equals(roundId))
                .reduce((re1, re2) -> {
                    throw new DuplicateKeyException(
                            "Found multiple round entities with id " + roundId);
                })
                .ifPresent(roundEntity -> {
                    roundEntity.getGames().forEach(games -> {
                        GameCompetitor game = new GameCompetitor();
                        game.setWinner(Optional.ofNullable(games.getWinner())
                                .map(CompetitorRoleEntity::getAccount)
                                .map(AccountEntity::getUsername)
                                .orElse(null));
                        game.setWinnerId(Optional.ofNullable(games.getWinner())
                                .map(CompetitorRoleEntity::getAccount)
                                .map(AccountEntity::getId)
                                .orElse(null));
                        for (int i = 0; i < games.getCompetitors().size(); i++) {
                            if (i % 2 == 0) {
                                game.setCompetitor1(
                                        games.getCompetitors()
                                                .get(i)
                                                .getAccount()
                                                .getUsername());
                                game.setCompetitor1Id(
                                        games.getCompetitors()
                                                .get(i)
                                                .getAccount()
                                                .getId());
                            } else {
                                game.setCompetitor2(
                                        games.getCompetitors()
                                                .get(i)
                                                .getAccount()
                                                .getUsername());
                                game.setCompetitor2Id(
                                        games.getCompetitors()
                                                .get(i)
                                                .getAccount()
                                                .getId());
                            }
                        }
                        gameCompetitor.add(game);
                    });
                    model.addAttribute("roundEnd", roundEntity.getEndTime()
                            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                });
        model.addAttribute("games", gameCompetitor);
        model.addAttribute("tournamentName", tournament.getName());
        model.addAttribute("tournamentFormat", tournament.getFormat());

        return "/roundScore";
    }

    public static class RoundParamsPlaceholder {
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime endDate = LocalDateTime.now().withSecond(0).withNano(0);

        public LocalDateTime getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
        }
    }

}

