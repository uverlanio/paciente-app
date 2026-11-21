package com.pereira.jaala.prontuarios.controller;

import com.pereira.jaala.prontuarios.dto.UsuarioDTO;
import com.pereira.jaala.prontuarios.model.Login;
import com.pereira.jaala.prontuarios.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class LoginController {

    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO, HttpSession session){
        try {
            Login usuarioLogin = loginService.findUsuario(usuarioDTO);
            if (Objects.isNull(usuarioLogin)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            session.setAttribute("usuarioId", usuarioLogin.getId());
            session.setAttribute("usuarioNome", usuarioLogin.getNome());
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("nome", usuarioLogin.getNome()));
        }catch (Exception e){
            System.out.println("Não foi possível fazer o login.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Void> loginForm(@ModelAttribute UsuarioDTO usuarioDTO, HttpSession session) {
        Login usuarioLogin = loginService.findUsuario(usuarioDTO);
        if (Objects.nonNull(usuarioLogin)) {
            session.setAttribute("usuarioId", usuarioLogin.getId());
            session.setAttribute("usuarioNome", usuarioLogin.getNome());
            return ResponseEntity.status(HttpStatus.SEE_OTHER)
                    .location(URI.create("/html/home.html"))
                    .build();
        }

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(URI.create("/html/login.html"))
                .build();
    }
}
