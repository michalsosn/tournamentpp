package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.service.FormatTournamentService;

import java.util.List;

/**
 * Created by dpp on 5/7/16.
 */
@Controller
@RestController
public class FormatTournamentController {

    @Autowired
    private FormatTournamentService formatTournamentService;

    @RequestMapping  (path = "/single", method = RequestMethod.GET)
    public String showExample() {
        return "singleElimination";
    }

    @RequestMapping (path = "/{tournamentId}/tree",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoundEntity> getTournamentTree(@PathVariable Long tournamentId) {
        List<RoundEntity> rounds = formatTournamentService.getRounds(tournamentId);
        return rounds;
    }

}
