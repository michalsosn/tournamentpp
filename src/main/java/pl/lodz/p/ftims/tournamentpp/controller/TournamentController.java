package pl.lodz.p.ftims.tournamentpp.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.service.TournamentDto;
import pl.lodz.p.ftims.tournamentpp.service.TournamentService;
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
            method = RequestMethod.POST)
    public String deleteTournament(@PathVariable long tournamentId) {

        tournamentService.deleteTournament(tournamentId);

        /*
         * TODO
         * na co przekierowywac
         */
        return "redirect:/organizer/createTournament";
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

    @RequestMapping(path = "/organizer/createTournament", method = RequestMethod.GET)
    public String createTournament(Model model) {
        model.addAttribute("tournament", new TournamentDto());
        return "/organizer/createTournament";
    }

    @RequestMapping(path = "/organizer/createTournament", method = RequestMethod.POST)
    public String createTournament(
            @Valid @ModelAttribute("tournament") TournamentDto tournament,
            BindingResult bindingResult,
            Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/organizer/createTournament";
        }
        tournamentService.createTournament(tournament, principal.getName());
        return "redirect:/organizer/createTournament";
    }

}


















