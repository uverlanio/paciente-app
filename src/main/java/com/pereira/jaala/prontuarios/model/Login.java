package com.pereira.jaala.prontuarios.model;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Document(collection = "usuario")
public class Login {

    @Id
    private String id;

    @Size(max = 15)
    private String nome;

    @Size(min = 4, max = 4)
    private String senha;


}
