package com.pereira.jaala.prontuarios.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @Column
    private String id;

    @Column(length = 50)
    @Size(max = 50)
    private String nome;

    @Column(columnDefinition = "LONGTEXT")
    @Size(max = 5000)
    private String prontuario;

    @Column(name = "ultima_atualizacao")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime ultimaAtualizacao;
}
