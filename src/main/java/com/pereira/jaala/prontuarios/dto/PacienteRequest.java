package com.pereira.jaala.prontuarios.dto;

import java.util.Objects;

public class PacienteRequest {

    private String id;
    private String nome;
    private String prontuario;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PacienteRequest that = (PacienteRequest) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(prontuario, that.prontuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, prontuario);
    }
}
