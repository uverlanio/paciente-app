package com.pereira.jaala.prontuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PacienteResponse {

    private String id;
    private String nome;
    private String prontuario;
    private LocalDateTime ultimaAtualizacao;

}
