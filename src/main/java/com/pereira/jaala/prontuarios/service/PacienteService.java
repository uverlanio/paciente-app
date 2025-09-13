package com.pereira.jaala.prontuarios.service;

import com.pereira.jaala.prontuarios.dto.PacienteRequest;
import com.pereira.jaala.prontuarios.dto.PacienteResponse;
import com.pereira.jaala.prontuarios.model.Paciente;
import com.pereira.jaala.prontuarios.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository repository;

    public PacienteResponse salvar(PacienteRequest request) {
        Paciente paciente = new Paciente();
        paciente.setId(request.getId());
        paciente.setNome(request.getNome());
        paciente.setProntuario(request.getProntuario());
        paciente.setUltimaAtualizacao(LocalDateTime.now());
        repository.save(paciente);
        return toResponse(paciente);
    }

    public PacienteResponse buscarPorIdOuNome(String termo) {
        Optional<Paciente> porId = repository.findById(termo);
        if (porId.isPresent()) return toResponse(porId.get());

        List<Paciente> porNome = repository.findByNomeContainingIgnoreCase(termo);
        return porNome.isEmpty() ? null : toResponse(porNome.get(0));
    }

    public PacienteResponse atualizar(String id, PacienteRequest request) {
        Paciente paciente = repository.findById(id).orElseThrow();
        paciente.setNome(request.getNome());
        paciente.setProntuario(request.getProntuario());
        paciente.setUltimaAtualizacao(LocalDateTime.now());
        repository.save(paciente);
        return toResponse(paciente);
    }

    private PacienteResponse toResponse(Paciente paciente) {
        return new PacienteResponse(
                paciente.getId(),
                paciente.getNome(),
                paciente.getProntuario(),
                paciente.getUltimaAtualizacao()
        );
    }
}
