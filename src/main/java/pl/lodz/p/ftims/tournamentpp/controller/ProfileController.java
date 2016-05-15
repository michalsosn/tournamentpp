package pl.lodz.p.ftims.tournamentpp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.repository.AccountRepository;
import pl.lodz.p.ftims.tournamentpp.service.AccountDto;
import pl.lodz.p.ftims.tournamentpp.service.AccountService;

@Controller
public class ProfileController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/profile")
    public String showData(Model model) {

        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String name = authentication.getName();
        Optional<AccountEntity> account = repository.findByUsername(name);
        if (account.isPresent()) {
            model.addAttribute("login", account.get().getUsername());
            model.addAttribute("email", account.get().getEmail());
            model.addAttribute("name", account.get().getName());
            model.addAttribute("phone", account.get().getPhone());
            model.addAttribute("birthday", account.get().getBirthdate());
            model.addAttribute("description", account.get().getDescription());
        }
        return "profile";
    }
    @RequestMapping(path = "/editprofile", method = RequestMethod.GET)
    public String makeEditProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String name = authentication.getName();
        Optional<AccountEntity> account = repository.findByUsername(name);
        model.addAttribute("account", account);
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

