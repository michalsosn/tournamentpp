package pl.lodz.p.ftims.tournamentpp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.service.AccountService;
import pl.lodz.p.ftims.tournamentpp.service.TournamentService;

@Controller
public class AccountTornamentController {

    @Autowired
    private AccountService accountService;

//    @Autowired
//    private TournamentService tournamentService;

    @RequestMapping(path = "/account/playerTournaments", method = RequestMethod.GET)
    public String showUserTournaments(@RequestParam(name = "page", defaultValue = "0")
        Integer page, Model model) {

        AccountEntity account = accountService.findAccount();
        CompetitorRoleEntity competitorRoleEntity =
               new CompetitorRoleEntity(true, account);

        final List<TournamentEntity> tournaments
        = competitorRoleEntity.getTournaments();

//        final Page<TournamentEntity> tournaments =
//                tournamentService.listTournaments(page);

        model.addAttribute("tournaments", tournaments);
        return "/account/playerTournaments";
    }


//  @RequestMapping(path = "/account/playerTournaments", method = RequestMethod.GET)
//      public String showUserTournaments(@RequestParam(name = "page", defaultValue = "0")
//      Integer page, Model model) {
//      return "/account/playerTournaments";
//  }

    @RequestMapping(path = "/roundScore", method = RequestMethod.GET)
    public String showRoundScore(Model model) {
        return "/roundScore";
    }


}
