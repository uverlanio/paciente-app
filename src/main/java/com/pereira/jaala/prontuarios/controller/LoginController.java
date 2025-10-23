package com.pereira.jaala.prontuarios.controller;

import com.pereira.jaala.prontuarios.dto.UsuarioDTO;
import com.pereira.jaala.prontuarios.model.Login;
import com.pereira.jaala.prontuarios.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class LoginController {

    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO){
        try {
            Login usuarioLogin = loginService.findUsuario(usuarioDTO);
            return Objects.nonNull(usuarioLogin) ? ResponseEntity.status(HttpStatus.ACCEPTED).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            System.out.println("Não foi possível fazer o login.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
