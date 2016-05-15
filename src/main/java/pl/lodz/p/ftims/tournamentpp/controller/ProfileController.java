package pl.lodz.p.ftims.tournamentpp.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.service.AccountDto;
import pl.lodz.p.ftims.tournamentpp.service.AccountService;

@Controller
public class ProfileController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String showData(Model model) {
        Optional<AccountEntity> account = accountService.showProfile();
        if (account.isPresent()) {
            model.addAttribute("username", account.get().getUsername());
            model.addAttribute("email", account.get().getEmail());
            model.addAttribute("name", account.get().getName());
            model.addAttribute("phone", account.get().getPhone());
            model.addAttribute("birthdate", account.get().getBirthdate());
            model.addAttribute("description", account.get().getDescription());
        }
        return "profile";
    }
    @RequestMapping(path = "/editprofile", method = RequestMethod.GET)
    public String showEditProfile(Model model) {
        model.addAttribute("account", accountService.showProfile());
        return "editprofile";
    }

    @RequestMapping(path = "/editprofile", method = RequestMethod.POST)
    public String editProfile(
            @Valid @ModelAttribute("account") AccountDto account,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "editprofile";
        }
        accountService.createAccount(account);
        return "redirect:/profile";
    }

}

