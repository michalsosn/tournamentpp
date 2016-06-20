package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.lodz.p.ftims.tournamentpp.entities.*;
import pl.lodz.p.ftims.tournamentpp.service.AccountService;
import pl.lodz.p.ftims.tournamentpp.service.TournamentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AccountTournamentController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TournamentService tournamentService;

    @RequestMapping(path = "/account/playerTournaments", method = RequestMethod.GET)
    public String showUserTournaments(@RequestParam(name = "page", defaultValue = "0")
                                      Model model) {
        AccountEntity account = accountService.findAccount();
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

    @RequestMapping(path = "/player/playerTournaments", method = RequestMethod.GET)
    public String showUserTournaments(@RequestParam("username") String username,
                                      Model model) {
        AccountEntity account = accountService.findAccountByUsername(username);
        Map<Role,RoleEntity> map = account.getRoles();
        RoleEntity role = map.get(Role.ROLE_COMPETITOR);
        final Iterable<TournamentEntity> tournaments =
                tournamentService.listAllTournaments();
        final Iterable<TournamentEntity> lastMonth =
                tournamentService.listLastMonthTournaments();

        model.addAttribute("tournaments", getUserTournaments(tournaments,
                role.getId()));
        model.addAttribute("last_month", getUserTournaments(lastMonth, role.getId()));
        return "/account/playerTournaments";
    }

    private List<TournamentWinners> getUserTournaments(Iterable<TournamentEntity>
                                                       tournaments, long id) {
         int winCount = 0;
         List<TournamentWinners> userTournaments = new ArrayList<>();
         for (TournamentEntity tournamentEntity : tournaments) {
             List<CompetitorRoleEntity> c = tournamentEntity.getCompetitors();
             for (int i = 0; i < c.size(); i++) {
                 if (c.get(i).getId() == id) {
                    for (RoundEntity roleEntity : tournamentEntity.getRounds()) {
                        for (GameEntity games : roleEntity.getGames()) {
                            if (games.getWinner().getId() == id) {
                               winCount++;
                            }
                        }
                    }
                    TournamentWinners winner =
                            new TournamentWinners(tournamentEntity, winCount);
                    userTournaments.add(winner);
                 }
             }
         }
         return userTournaments;
    }

    @RequestMapping(path = "/organizer/addPlayer/{tournamentId}",
            method = RequestMethod.GET)
    public String addPlayer(Model model, @PathVariable long tournamentId) {

        List<AccountEntity> players = accountService.listPlayersToAdd(tournamentId);

        model.addAttribute("players", players);
        model.addAttribute("tournamentId", tournamentId);

        return "tournament/addPlayer";
    }

    @RequestMapping(path = "/organizer/addPlayer/{tournamentId}/{playerID}",
            method = RequestMethod.GET)
    public String addPlayer(Model model, @PathVariable long tournamentId,
            @PathVariable long playerID) {

        tournamentService.addPlayer(tournamentId, playerID);

        return "redirect:/organizer/addPlayer/{tournamentId}";
    }

    @RequestMapping(path = "/organizer/deletePlayer/{tournamentId}",
            method = RequestMethod.GET)
    public String deletePlayer(Model model, @PathVariable long tournamentId) {

        List<AccountEntity> players = accountService.listPlayersToDelete(tournamentId);

        model.addAttribute("players", players);
        model.addAttribute("tournamentId", tournamentId);

        return "tournament/deletePlayer";
    }

    @RequestMapping(path = "/organizer/deletePlayer/{tournamentId}/{playerID}",
            method = RequestMethod.GET)
    public String deletePlayer(Model model, @PathVariable long tournamentId,
            @PathVariable long playerID) {

        tournamentService.deletePlayer(tournamentId, playerID);

        return "redirect:/organizer/deletePlayer/{tournamentId}";
    }

}
