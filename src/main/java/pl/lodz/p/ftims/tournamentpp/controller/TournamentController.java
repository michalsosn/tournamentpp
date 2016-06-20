package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.service.TournamentService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;


    @RequestMapping(path = {"/", "/tournament/tournaments"}, method = RequestMethod.GET)
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

    @RequestMapping(path = "/support/tournament/{id}/round",
            method = RequestMethod.POST)
    public String addNewRound(
            @PathVariable long id,
            @Valid @ModelAttribute("roundParams") RoundParamsPlaceholder roundParams
    ) {
        tournamentService.generateRound(id, roundParams.getEndDate());
        return "redirect:/tournament/tournament/" + id;
    }

    @RequestMapping(path = "/tournament/tournament/{tournamentId}/round/{roundId}",
            method = RequestMethod.GET)
    public String showRoundScore(
            @PathVariable long tournamentId, @PathVariable long roundId, Model model
    ) {
        final TournamentService.RoundScoreResult result =
                tournamentService.findRoundScore(tournamentId, roundId);
        model.addAttribute("games", result.getGames());
        model.addAttribute("round", result.getRound());
        model.addAttribute("tournament", result.getTournament());
        return "/tournament/roundScore";
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
