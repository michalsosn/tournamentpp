package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by dpp on 5/7/16.
 */
@Controller
public class FormatTournamentController {

    @RequestMapping(path = "/single", method = RequestMethod.GET)
    public String showExample() {
        return "singleElimination";
    }

}
