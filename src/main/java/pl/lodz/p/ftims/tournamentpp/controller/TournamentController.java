package pl.lodz.p.ftims.tournamentpp.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.service.TournamentService;
import pl.lodz.p.ftims.tournamentpp.service.dto.TournamentDto;
import pl.lodz.p.ftims.tournamentpp.trees.Format;

@Controller
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @ModelAttribute("allFormats")
    public List<Format> populateFormats() {
        return Arrays.asList(Format.values());
    }

    @RequestMapping(path = "/organizer/deleteTournament/{tournamentId}",
            method = RequestMethod.GET)
    public String deleteTournament(@PathVariable long tournamentId) {

        tournamentService.deleteTournament(tournamentId);

        /*
         * TODO
         * na co przekierowywac
         */
        return "redirect:/tournament/tournaments";
    }

    @RequestMapping(path = "/organizer/updateTournament/{tournamentId}",
            method = RequestMethod.GET)
    public String updateTournament(Model model, @PathVariable long tournamentId) {
        //TournamentDto tournamentDto = tournamentService.getTournamentDto(tournamentId);
        TournamentEntity tournament = tournamentService.findTournament(tournamentId);

        model.addAttribute("tournament", tournament);
        model.addAttribute("tournamentId", tournamentId);

        return "tournament/updateTournament";
    }

    @RequestMapping(path = "/organizer/updateTournament/{tournamentId}",
            method = RequestMethod.POST)
    public String updateTournament(
            @Valid @ModelAttribute("tournament") TournamentDto tournament,
            BindingResult bindingResult,
            @PathVariable long tournamentId,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            return "redirect:/organizer/createTournament";
        }
        tournamentService.updateTournament(tournament, tournamentId, principal.getName());

        /*
         * TODO
         * na co przekierowywac
         */
        return "redirect:/tournament/tournament/{tournamentId}";
    }

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
