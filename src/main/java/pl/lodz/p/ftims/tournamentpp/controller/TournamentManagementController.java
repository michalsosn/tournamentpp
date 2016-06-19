package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.service.GameService;
import pl.lodz.p.ftims.tournamentpp.service.TournamentService;

/**
 * @author Michał Sośnicki
 */
@Controller
public class TournamentManagementController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private GameService gameService;

    @RequestMapping(path = "/support/updateResults/{roundId}", method = RequestMethod.GET)
    public String getResults(@PathVariable long roundId, Model model) {
        final RoundEntity round = gameService.findRound(roundId);
        model.addAttribute("round", round);
        return "/support/updateResults";
    }

    @RequestMapping(path = "/support/updateResults/{roundId}",
                    method = RequestMethod.POST)
    public String updateResults(@PathVariable long roundId, Model model) {
        // do something plz
        return "/support/updateResults";
    }

}
