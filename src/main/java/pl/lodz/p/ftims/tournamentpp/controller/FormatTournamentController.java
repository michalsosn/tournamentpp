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
    public String showSingleElimantionExample() {
        return "singleEliminationExample";
    }

    @RequestMapping(path = "example/double", method = RequestMethod.GET)
    public String showDoubleElimantionExample() {
        return "doubleEliminationExample";
    }

    @RequestMapping(path = "example/robin", method = RequestMethod.GET)
    public String showRoundRobinExample() {
        return "roundRobinExample";
    }

    @RequestMapping(path = "tournament/tournament/{tournamentId}/tree",
            method = RequestMethod.GET)
    public String getTournamentTree(@PathVariable long tournamentId, Model model) {
        model.addAttribute("tournamentId", tournamentId);
        model.addAttribute("type", formatTournamentService
                .getTournament(tournamentId).getFormat());

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
