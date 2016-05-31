package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.service.TournamentService;

/**
 * @author Michał Sośnicki
 */
@Controller
public class TournamentOrganizationController {

    @Autowired
    private TournamentService tournamentService;

    // TODO to może być przydatne do wyświetlenia listy turniejów!
    @RequestMapping(path = "/tournament/tournaments", method = RequestMethod.GET)
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
        return "/tournament/tournament";
    }

    @RequestMapping(path = "/tournament/tournament/{id}/round",
            method = RequestMethod.POST)
    public String addNewRound(@PathVariable long id) {
        tournamentService.generateRound(id);
        return "redirect:/tournament/tournament/" + id;
    }

}

