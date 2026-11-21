package com.pereira.jaala.prontuarios.service;

import com.pereira.jaala.prontuarios.dto.PacienteRequest;
import com.pereira.jaala.prontuarios.dto.PacienteResponse;
import com.pereira.jaala.prontuarios.model.Paciente;
import com.pereira.jaala.prontuarios.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteService service;

    @Test
    void deveGerarIdAoCriarProntuarioSemIdInformado() {
        PacienteRequest request = new PacienteRequest();
        request.setNome("Paciente");
        request.setProntuario("Registro inicial");
        when(repository.save(any(Paciente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PacienteResponse resposta = service.salvar(request);

        assertNotNull(resposta.getId());
        assertEquals(resposta.getId(), UUID.fromString(resposta.getId()).toString());
        verify(repository).save(any(Paciente.class));
    }

    @Test
    void deveBuscarPacientePeloId() {
        Paciente paciente = new Paciente();
        paciente.setId("paciente-id");
        paciente.setNome("Paciente");
        paciente.setProntuario("Registro");
        when(repository.findById("paciente-id")).thenReturn(Optional.of(paciente));

        PacienteResponse resposta = service.buscarPorIdOuNome("paciente-id");

        assertEquals("paciente-id", resposta.getId());
        verify(repository).findById("paciente-id");
    }
}
