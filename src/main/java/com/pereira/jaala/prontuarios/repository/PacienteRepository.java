package com.pereira.jaala.prontuarios.repository;

import com.pereira.jaala.prontuarios.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {

    List<Paciente> findByNomeContainingIgnoreCase(String nome);

}
