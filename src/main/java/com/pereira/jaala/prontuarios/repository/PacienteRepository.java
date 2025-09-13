package com.pereira.jaala.prontuarios.repository;

import com.pereira.jaala.prontuarios.model.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends MongoRepository<Paciente, String> {

    List<Paciente> findByNomeContainingIgnoreCase(String nome);

}
