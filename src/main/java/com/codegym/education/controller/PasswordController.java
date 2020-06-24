package com.codegym.education.controller;

import com.codegym.education.model.Participant;
import com.codegym.education.service.EmailService;
import com.codegym.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
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
            Participant participant = optional.get();
            participant.setResetToken(UUID.randomUUID().toString());

            userService.save(participant);

            String appUrl = request.getScheme() + "://" + request.getServerName();
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("doubleteamc0220h1@gmail.com");
            passwordResetEmail.setTo(participant.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + ":8080/reset?token=" + participant.getResetToken());

            emailService.sendEmail(passwordResetEmail);
            modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
        }
        modelAndView.setViewName("forgotPassword");
        return modelAndView;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        Optional<Participant> participant = userService.findUserByResetToken(token);

        if (participant.isPresent()) {
            modelAndView.addObject("resetToken", token);
        } else {
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
        }

        modelAndView.setViewName("resetPassword");
        return modelAndView;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

        // Find the user associated with the reset token
        Optional<Participant> participant = userService.findUserByResetToken(requestParams.get("token"));

        // This should always be non-null but we check just in case
        if (participant.isPresent()) {

            Participant resetParticipant = participant.get();

            // Set new password
            resetParticipant.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

            // Set the reset token to null so it cannot be used again
            resetParticipant.setResetToken(null);

            // Save user
            userService.save(resetParticipant);

            // In order to set a model attribute on a redirect, we must use
            // RedirectAttributes
            redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

            modelAndView.setViewName("redirect:login");
            return modelAndView;

        } else {
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
            modelAndView.setViewName("resetPassword");
        }

        return modelAndView;
    }

    // Going to reset page without a token redirects to login page
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:login");
    }
}
