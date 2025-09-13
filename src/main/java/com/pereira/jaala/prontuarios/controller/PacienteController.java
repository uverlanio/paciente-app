package com.pereira.jaala.prontuarios.controller;

import com.pereira.jaala.prontuarios.dto.PacienteRequest;
import com.pereira.jaala.prontuarios.dto.PacienteResponse;
import com.pereira.jaala.prontuarios.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @PostMapping
    public ResponseEntity<PacienteResponse> criar(@RequestBody PacienteRequest request) {
        return ResponseEntity.ok(service.salvar(request));
    }

    @GetMapping("/{termo}")
    public ResponseEntity<PacienteResponse> buscar(@PathVariable String termo) {
        return ResponseEntity.ok(service.buscarPorIdOuNome(termo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> atualizar(@PathVariable String id, @RequestBody PacienteRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }
}
