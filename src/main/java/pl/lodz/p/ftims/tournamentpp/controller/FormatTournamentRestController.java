package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.service.FormatTournamentService;

import java.util.List;

/**
 * Created by Daniel on 2016-05-12.
 */
@Controller
@RestController
public class FormatTournamentRestController {

    @Autowired
    private FormatTournamentService formatTournamentService;

    @RequestMapping(path = "/{tournamentId}/rounds",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoundEntity> getTournamentRounds(@PathVariable Long tournamentId) {
        List<RoundEntity> rounds = formatTournamentService.getRounds(tournamentId);
        return rounds;
    }

}
