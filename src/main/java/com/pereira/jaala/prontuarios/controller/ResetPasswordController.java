package com.pereira.jaala.prontuarios.controller;

import com.pereira.jaala.prontuarios.dto.UsuarioDTO;
import com.pereira.jaala.prontuarios.model.Login;
import com.pereira.jaala.prontuarios.service.ResetPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class ResetPasswordController {

    ResetPasswordService resetPasswordService;

    public ResetPasswordController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    @PutMapping(value = "/reset", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> reset(@RequestBody UsuarioDTO usuarioDTO){
        try {
            Login usuarioReset = resetPasswordService.findUsuarioWithoutSenha(usuarioDTO);
            return Objects.nonNull(usuarioReset) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            System.out.println("Não foi possível resetar a senha.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/reset", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Void> resetForm(@ModelAttribute UsuarioDTO usuarioDTO) {
        Login usuarioReset = resetPasswordService.findUsuarioWithoutSenha(usuarioDTO);
        String destino = Objects.nonNull(usuarioReset) ? "/html/login.html" : "/html/reset.html";
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(URI.create(destino))
                .build();
    }
}
