package com.codegym.education.controller;

import com.codegym.education.model.Participant;
import com.codegym.education.service.EmailService;
import com.codegym.education.service.RoleService;
import com.codegym.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PasswordController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    private ModelAndView displayForgotPasswordPage() {
        return new ModelAndView("forgotPassword");
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request) {
        Optional<Participant> optional = userService.findUserByEmail(userEmail);

        if (!optional.isPresent()) {
            modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
        } else {
            Participant user = optional.get();
            user.setResetToken(UUID.randomUUID().toString());

            userService.save(user);

            String appUrl = request.getScheme() + "://" + request.getServerName();
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("doubleteamc0220h1@gmail.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + ":8080/reset?token=" + user.getResetToken());

            emailService.sendEmail(passwordResetEmail);
            modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
        }
        modelAndView.setViewName("forgotPassword");
        return modelAndView;
    }
}
