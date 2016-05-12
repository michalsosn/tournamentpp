package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lodz.p.ftims.tournamentpp.service.FormatTournamentService;

/**
 * Created by dpp on 5/7/16.
 */
@Controller
public class FormatTournamentController {

    @Autowired
    private FormatTournamentService formatTournamentService;

    @RequestMapping(path = "/single", method = RequestMethod.GET)
    public String showExample() {
        return "singleElimination";
    }

    @RequestMapping(path = "/{tournamentId}/tree",
            method = RequestMethod.GET)
    public String getTournamentTree(@PathVariable long tournamentId, Model model) {
        model.addAttribute("tournamentId", tournamentId);
        return "formatTree";
    }
}
