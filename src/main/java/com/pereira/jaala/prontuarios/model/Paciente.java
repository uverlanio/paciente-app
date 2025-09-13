package com.pereira.jaala.prontuarios.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "pacientes")
public class Paciente {

    @Id
    private String id;

    @Size(max = 50)
    private String nome;

    @Size(max = 5000)
    private String prontuario;

    private LocalDateTime ultimaAtualizacao;
}
