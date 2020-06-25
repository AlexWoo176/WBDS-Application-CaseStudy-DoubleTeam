package com.codegym.education.controller;

import com.codegym.education.model.Participant;
import com.codegym.education.model.Role;
import com.codegym.education.service.EmailService;
import com.codegym.education.service.RoleService;
import com.codegym.education.service.UserService;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleService roleService;

    private static final String DEFAULT_ROLE = "ROLE_USER";

    @GetMapping(value = {"/index"})
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("participant", userService.getCurrentUser());
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView loginForm() {
        return new ModelAndView("login");
    }

    @PostMapping("/login-form")
    public ModelAndView login(@ModelAttribute("participant") Participant participant, HttpServletRequest request) {

        ModelAndView modelAndView;
        /**/
        if (userService.checkLogin(participant)) {
            modelAndView = new ModelAndView("redirect:/");
            HttpSession session = request.getSession();
            session.setAttribute("participant", participant);
            modelAndView.addObject("session", session);

            //modelAndView.addObject("participant", participant);
            return modelAndView;
        }
        modelAndView = new ModelAndView("login");
        modelAndView.addObject("message", "username or password incorrect");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, Participant participant) {
        modelAndView.addObject("participant", participant);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid Participant participant, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Participant> userExists = userService.findUserByEmail(participant.getEmail());
        boolean isRegistered = false;
        System.out.println(userExists);

        if (userExists.isPresent()) {
            modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
            modelAndView.setViewName("register");
            bindingResult.reject("email");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else {
            participant.setEnabled(false);
            isRegistered = true;
            participant.setConfirmationToken(UUID.randomUUID().toString());

            Role role = roleService.findRoleByName(DEFAULT_ROLE);
            participant.setRole(role);

            userService.save(participant);

            String appUrl = request.getScheme() + "://" + request.getServerName();

            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(participant.getEmail());
            registrationEmail.setSubject("Registration Confirmation");
            registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                    + appUrl + ":8080/confirm?token=" + participant.getConfirmationToken());
            registrationEmail.setFrom("doubleteamc0220h1@gmail.com");

            emailService.sendEmail(registrationEmail);

            modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to " + participant.getEmail());
            modelAndView.addObject("confirm", "https://mail.google.com/mail/u/0/#inbox");
            modelAndView.setViewName("register");
        }
        modelAndView.addObject("isRegistered", isRegistered);
        return modelAndView;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView confirmRegistration(ModelAndView modelAndView, @RequestParam("token") String token) {

        Participant participant = userService.findByConfirmationToken(token);

        if (participant == null) {
            modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
        } else {
            modelAndView.addObject("confirmationToken", participant.getConfirmationToken());
        }

        modelAndView.setViewName("confirm");
        return modelAndView;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ModelAndView confirmRegistration(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map<String, String> requestParams, RedirectAttributes redirect) {

        modelAndView.setViewName("confirm");

        Zxcvbn passwordCheck = new Zxcvbn();

        Strength strength = passwordCheck.measure(requestParams.get("password"));

        if (strength.getScore() < 1) {
            bindingResult.reject("password");

            redirect.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");

            modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
            System.out.println(requestParams.get("token"));
            return modelAndView;
        }
        Participant participant = userService.findByConfirmationToken(requestParams.get("token"));
        participant.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
        participant.setEnabled(true);
        userService.save(participant);
        modelAndView.addObject("successMessage", "Your password has been set!");
        modelAndView.setViewName("redirect:login");
        return modelAndView;
    }
}