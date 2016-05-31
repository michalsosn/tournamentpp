package pl.lodz.p.ftims.tournamentpp.controller;

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

    @RequestMapping(path = "/createTournament", method = RequestMethod.GET)
    public String createTournament(Model model) {
        model.addAttribute("tournament", new TournamentDto());
        // model.addAllAttributes(populateFormats());
        return "createTournament";
    }

    @RequestMapping(path = "/createTournament", method = RequestMethod.POST)
    public String createTournament(@Valid
            @ModelAttribute("tournament") TournamentDto tournament,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createTournament";
        }
        tournamentService.createTournament(tournament);
        return "redirect:/createTournament";
    }

    @RequestMapping(path = "/deleteTournament/{tournamentId}",
            method = RequestMethod.POST)
    public String deleteTournament(@PathVariable long tournamentId) {

        tournamentService.deleteTournament(tournamentId);

        /*
         * TODO
         * na co przekierowywac
         */
        return "redirect:/createTournament";
    }

    @RequestMapping(path = "/updateTournament/{tournamentId}",
            method = RequestMethod.GET)
    public String updateTournament(Model model, @PathVariable long tournamentId) {
        TournamentDto tournamentDto = tournamentService.getTournamentDto(tournamentId);
        model.addAttribute("tournament", tournamentDto);

        return "updateTournament";
    }

    @RequestMapping(path = "/deleteTournament/{tournamentId}",
            method = RequestMethod.POST)
    public String updateTournament(@Valid
            @ModelAttribute("tournament") TournamentDto tournament,
            BindingResult bindingResult, @PathVariable long tournamentId) {
        if (bindingResult.hasErrors()) {
            return "createTournament";
        }
        tournamentService.updateTournament(tournament, tournamentId);

        /*
         * TODO
         * na co przekierowywac
         */
        return "redirect:/createTournament";
    }
}


















