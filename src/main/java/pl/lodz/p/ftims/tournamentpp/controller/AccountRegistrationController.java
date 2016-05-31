package pl.lodz.p.ftims.tournamentpp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.service.AccountDto;
import pl.lodz.p.ftims.tournamentpp.service.AccountService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author Michał Sośnicki
 */
@Controller
public class AccountRegistrationController {

    @Autowired
    private AccountService accountService;

    @ModelAttribute("allRoles")
    public List<Role> populateRoles() {
        return Arrays.asList(Role.values());
    }

    @RequestMapping(path = "/signin", method = RequestMethod.GET)
    public String signInAccount() {
        return "signin";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String registerAccount(Model model) {
        model.addAttribute("account", new AccountDto());
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String registerAccount(
            @Valid @ModelAttribute("account") AccountDto account,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        accountService.createAccount(account);
        return "redirect:/register";
    }

}
