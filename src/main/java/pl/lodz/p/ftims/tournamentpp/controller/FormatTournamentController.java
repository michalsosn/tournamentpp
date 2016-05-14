package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.service.FormatTournamentService;
import pl.lodz.p.ftims.tournamentpp.service.RoundDto;
import pl.lodz.p.ftims.tournamentpp.trees.TorunamentType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dpp on 5/7/16.
 */
@Controller
public class FormatTournamentController {

    @Autowired
    private FormatTournamentService formatTournamentService;

    @RequestMapping(path = "example/single", method = RequestMethod.GET)
    public String showExample() {
        return "singleElimination";
    }

    @RequestMapping(path = "/{tournamentId}/tree",
            method = RequestMethod.GET)
    public String getTournamentTree(@PathVariable long tournamentId, Model model) {
        model.addAttribute("tournamentId", tournamentId);
        model.addAttribute("type", TorunamentType.SINGLE_ELIMINATION);
        List<RoundDto> rounds = new ArrayList<>();

        int i = 1;
        for (RoundEntity r : formatTournamentService.getRounds(tournamentId)) {
            rounds.add(new RoundDto(r.getGames(), "Round " + i, r.getStartTime()));
            i++;
        }
        model.addAttribute("rounds", rounds);
        return "formatTree";
    }
}
