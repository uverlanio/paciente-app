package com.pereira.jaala.prontuarios.controller;

import com.pereira.jaala.prontuarios.dto.UsuarioDTO;
import com.pereira.jaala.prontuarios.model.Login;
import com.pereira.jaala.prontuarios.service.ResetPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class ResetPasswordController {

    ResetPasswordService resetPasswordService;

    public ResetPasswordController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    @PutMapping("/reset")
    ResponseEntity<?> reset(@RequestBody UsuarioDTO usuarioDTO){
        try {
            Login usuarioReset = resetPasswordService.findUsuarioWithoutSenha(usuarioDTO);
            return Objects.nonNull(usuarioReset) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            System.out.println("Não foi possível resetar a senha.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
