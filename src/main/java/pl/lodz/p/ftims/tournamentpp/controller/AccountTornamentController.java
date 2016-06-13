package pl.lodz.p.ftims.tournamentpp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.entities.RoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.service.AccountService;
import pl.lodz.p.ftims.tournamentpp.service.TournamentService;

@Controller
public class AccountTornamentController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TournamentService tournamentService;

    @RequestMapping(path = "/account/playerTournaments", method = RequestMethod.GET)
    public String showUserTournaments(@RequestParam(name = "page", defaultValue = "0")
        Integer page, Model model) {
        AccountEntity account = accountService.findAccount();
        CompetitorRoleEntity competitorRoleEntity =
               new CompetitorRoleEntity(true, account);
        Map<Role,RoleEntity> map = account.getRoles();
        RoleEntity role = map.get(Role.ROLE_COMPETITOR);
        final Iterable<TournamentEntity> tournaments =
              tournamentService.listAllTournaments();
        final Iterable<TournamentEntity> lastMonth =
                tournamentService.listLastMonthTournaments();

        model.addAttribute("tournaments", getUserTournaments(tournaments, role.getId()));
        model.addAttribute("last_month", getUserTournaments(lastMonth, role.getId()));
        return "/account/playerTournaments";
    }

    @RequestMapping(path = "/roundScore", method = RequestMethod.GET)
    public String showRoundScore(Model model) {
        return "/roundScore";
    }


    private List<TournamentEntity> getUserTournaments(Iterable<TournamentEntity>
                                                       tournaments, long id) {
         List<TournamentEntity> userTournaments = new ArrayList<TournamentEntity>();
         for (TournamentEntity tournamentEntity : tournaments) {
             List<CompetitorRoleEntity> c = tournamentEntity.getCompetitors();
             for (int i = 0; i < c.size(); i++) {
                 if (c.get(i).getId() == id) {
                    userTournaments.add(tournamentEntity);
                 }
             }
         }
         return userTournaments;
    }

}
