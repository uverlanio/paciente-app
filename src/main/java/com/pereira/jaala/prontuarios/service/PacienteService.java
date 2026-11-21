package com.pereira.jaala.prontuarios.service;

import com.pereira.jaala.prontuarios.dto.PacienteRequest;
import com.pereira.jaala.prontuarios.dto.PacienteResponse;
import com.pereira.jaala.prontuarios.model.Paciente;
import com.pereira.jaala.prontuarios.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        paciente.setId(request.getId() == null || request.getId().isBlank()
                ? UUID.randomUUID().toString()
                : request.getId());
        paciente.setNome(request.getNome());
        paciente.setProntuario(request.getProntuario());
        paciente.setUltimaAtualizacao(LocalDateTime.now());
        repository.save(paciente);
        return toResponse(paciente);
    }

    public PacienteResponse buscarPorIdOuNome(String termo) {
        Optional<Paciente> porId = repository.findById(termo);
        if (porId.isPresent()) {
            return toResponse(porId.get());
        }

        List<Paciente> porNome = repository.findByNomeContainingIgnoreCase(termo);

        if (porNome.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado.");
        }
        return toResponse(porNome.get(0));
    }

    public PacienteResponse atualizar(String id, PacienteRequest request) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));
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
