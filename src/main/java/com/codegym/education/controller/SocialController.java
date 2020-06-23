package com.codegym.education.controller;

import com.codegym.education.model.GooglePojo;
import com.codegym.education.model.Participant;
import com.codegym.education.service.*;
import com.codegym.education.service.impl.GoogleUtils;
import com.codegym.education.service.impl.RestFB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Controller
public class SocialController {
    private static final String USER_REGISTER = "user/register";
    private static final String ERROR_404 = "error-404";
    private static final String MESSAGE = "message";
    private static final String DEFAULT_ROLE = "ROLE_USER";
    private static final String TEXT = "To confirm your account, please click here : "
            + "http://localhost:8080/confirm-account?token=";

    private static final String SUBJECT = "Complete Registration!";

    @Autowired
    private UserService userService;

    @Autowired
    private RestFB restFB;

    @Autowired
    private GoogleUtils googleUtils;



    @PostMapping("/login-facebook")
    public String loginFacebook(HttpServletRequest request, Model model) {
        String code = request.getParameter("code");
        String accessToken = "";
        try {
            accessToken = restFB.getToken(code);
        } catch (IOException e) {
            return "login?facebook=error";
        }
        com.restfb.types.User user = restFB.getUserInfo(accessToken);
        UserDetails userDetail = restFB.buildUser(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Participant participant = new Participant();
        participant.setFullName(user.getName());
        participant.setPassword(randomAlphabetic(8));
        userService.save(participant);
        model.addAttribute("participant", participant);
        return "redirect:index";
    }

    @RequestMapping(value = "/login-google", method = RequestMethod.GET)
    public String loginGoogle(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            return "redirect:login?google=error";
        }
        String accessToken = googleUtils.getToken(code);

        GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
        UserDetails userDetail = googleUtils.buildUser(googlePojo);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:index";
    }
}

