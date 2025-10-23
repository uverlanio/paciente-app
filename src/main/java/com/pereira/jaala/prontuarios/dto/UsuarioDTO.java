package com.pereira.jaala.prontuarios.dto;

import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsuarioDTO {

    private static final String PASSWORD_REGEX = "^\\d{4}$";

    private String nome;

    @Pattern(regexp = PASSWORD_REGEX, message = "A senha deve conter 4 números")
    private String senha;

    @Pattern(regexp = PASSWORD_REGEX, message = "A senha deve conter 4 números")
    private String novaSenha;
}
