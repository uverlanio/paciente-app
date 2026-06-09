package com.pereira.jaala.prontuarios.dto;

import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
public class PacienteResponse {

    private String id;
    private String nome;
    private String prontuario;
    private LocalDateTime ultimaAtualizacao;

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

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PacienteResponse that = (PacienteResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(prontuario, that.prontuario) && Objects.equals(ultimaAtualizacao, that.ultimaAtualizacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, prontuario, ultimaAtualizacao);
    }
}
