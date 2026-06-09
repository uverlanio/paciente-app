package com.pereira.jaala.prontuarios.dto;

import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class UsuarioDTO {

    private static final String PASSWORD_REGEX = "^\\d{4}$";

    private String nome;

    @Pattern(regexp = PASSWORD_REGEX, message = "A senha deve conter 4 números")
    private String senha;

    @Pattern(regexp = PASSWORD_REGEX, message = "A senha deve conter 4 números")
    private String novaSenha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDTO that = (UsuarioDTO) o;
        return Objects.equals(nome, that.nome) && Objects.equals(senha, that.senha) && Objects.equals(novaSenha, that.novaSenha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, senha, novaSenha);
    }
}
