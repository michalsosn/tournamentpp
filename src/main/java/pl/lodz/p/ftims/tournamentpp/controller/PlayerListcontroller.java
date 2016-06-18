package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.lodz.p.ftims.tournamentpp.service.AccountSearchService;

/**
 * Created by Daniel on 2016-06-18.
 */
@Controller
public class PlayerListcontroller {
    @Autowired
    private AccountSearchService accountSearchService;

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String getStuffPaged(@RequestParam("username") String username, Pageable pager, Model model)
    {
        model.addAttribute("pathingVar", username);
        model.addAttribute("players", accountSearchService.findBySearchTerm(username, pager));
        return "/search/PlayerList";
    }
}
