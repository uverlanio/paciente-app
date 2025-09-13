package com.pereira.jaala.prontuarios.dto;

import lombok.Data;

@Data
public class PacienteRequest {

    private String id;
    private String nome;
    private String prontuario;
}
