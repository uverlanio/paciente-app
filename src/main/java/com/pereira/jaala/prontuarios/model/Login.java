package com.pereira.jaala.prontuarios.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "usuario")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15)
    @Size(max = 15)
    private String nome;

    @Column(length = 4)
    @Size(min = 4, max = 4)
    private String senha;



}
