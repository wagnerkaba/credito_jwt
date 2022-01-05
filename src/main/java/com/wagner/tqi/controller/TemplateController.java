package com.wagner.tqi.controller;

import com.wagner.tqi.person.dto.request.PersonDTO;
import com.wagner.tqi.person.entity.Person;
import com.wagner.tqi.user.ApplicationUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("/inicio/login") // vide classe ApplicationSecurityConfig. Lá indica a pagina de login (.loginPage("/inicio"))
    public String getLoginView(){
        System.out.println("pagina de login");
        return "pagina_de_login"; //esta String deve corresponder ao nome da pagina de login no diretório "resources/templates"
    }

    @GetMapping("/home")
    public String getCourses(Model model){

        String loggedUser = ApplicationUserDetailsService.getLoggedUser();

        if (loggedUser==null) loggedUser="Usuário não logado";

        model.addAttribute("email", loggedUser);

        System.out.println("home");
        return "home";
    }









}
