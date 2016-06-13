package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.service.AccountService;
import pl.lodz.p.ftims.tournamentpp.service.ProfileDto;

import javax.validation.Valid;

/**
 * @author Łukasz Kluch
 */
@Controller
public class AccountEditController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/account/account", method = RequestMethod.GET)
    public String showAccount(Model model) {
        AccountEntity account = accountService.findAccount();
        model.addAttribute("account", account);
        return "/account/account";
    }

    @RequestMapping(path = "/account/editAccount", method = RequestMethod.GET)
    public String showEditAccount(Model model) {
        AccountEntity account = accountService.findAccount();
        model.addAttribute("account", account);
        return "/account/editAccount";
    }

    @RequestMapping(path = "/account/editAccount", method = RequestMethod.POST)
    public String editAccount(
            @Valid @ModelAttribute("account") ProfileDto account,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "/account/editAccount";
        }
        accountService.updateAccount(account);
        return "redirect:/account/account";
    }

}

