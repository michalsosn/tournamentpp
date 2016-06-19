package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lodz.p.ftims.tournamentpp.service.dto.TournamentDto;
import pl.lodz.p.ftims.tournamentpp.service.TournamentService;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author Michał Sośnicki
 */
@Controller
public class TournamentOrganizationController {

    @Autowired
    private TournamentService tournamentService;

    @RequestMapping(path = "/organizer/createTournament", method = RequestMethod.GET)
    public String createTournament(Model model) {
        model.addAttribute("tournament", new TournamentDto());
        return "/organizer/createTournament";
    }

    @RequestMapping(path = "/organizer/createTournament", method = RequestMethod.POST)
    public String createTournament(
            @Valid @ModelAttribute("tournament") TournamentDto tournament,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "/organizer/createTournament";
        }
        tournamentService.createTournament(tournament, principal.getName());
        return "redirect:/organizer/createTournament";
    }

}

