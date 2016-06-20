package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.service.ApplicationService;
import pl.lodz.p.ftims.tournamentpp.service.TournamentService;
import pl.lodz.p.ftims.tournamentpp.service.dto.ApplicationDto;
import pl.lodz.p.ftims.tournamentpp.service.dto.IdForm;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ApplicationController {

    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(path = "/competitor/application/{tournamentId}",
                    method = RequestMethod.GET)
    public String applyForTournament(@PathVariable long tournamentId, Model model) {
        final TournamentEntity tournament
                = tournamentService.findTournament(tournamentId);
        model.addAttribute("tournament", tournament);
        model.addAttribute("applicationDto", new ApplicationDto());
        return "/competitor/application";
    }

    @RequestMapping(path = "/competitor/application/{tournamentId}",
                    method = RequestMethod.POST)
    public String applyForTournament(
            @PathVariable long tournamentId,
            @Valid @ModelAttribute("applicationDto") ApplicationDto applicationDto,
            Principal principal,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "/competitor/application";
        }
        applicationService.saveApplication(
                tournamentId, principal.getName(), applicationDto
        );
        return "redirect:/tournament/tournament/" + tournamentId;
    }

    @RequestMapping(path = "/organizer/applications/{tournamentId}",
                    method = RequestMethod.GET)
    public String acceptApplication(@PathVariable long tournamentId, Model model) {
        final TournamentEntity tournament =
                applicationService.findApplications(tournamentId);
        model.addAttribute("tournament", tournament);
        return "/organizer/applications";
    }

    @RequestMapping(path = "/organizer/applications/{tournamentId}",
                    method = RequestMethod.POST)
    public String acceptApplication(
            @PathVariable long tournamentId,
            @Valid @ModelAttribute("application") IdForm applicationId
    ) {
        applicationService.acceptApplication(applicationId.getId());
        return "redirect:/organizer/applications/" + tournamentId;
    }

}
