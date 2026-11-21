package com.pereira.jaala.prontuarios.controller;

import com.pereira.jaala.prontuarios.model.PerfilProfissional;
import com.pereira.jaala.prontuarios.service.PerfilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PerfilControllerTest {

    @Mock
    private PerfilService perfilService;

    private PerfilController controller;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        controller = new PerfilController(perfilService);
        session = new MockHttpSession();
        session.setAttribute("usuarioId", 7L);
    }

    @Test
    void deveRetornarFotoDoUsuarioAutenticado() {
        byte[] conteudo = {1, 2, 3};
        PerfilProfissional perfil = new PerfilProfissional();
        perfil.setFoto(conteudo);
        perfil.setTipoFoto(MediaType.IMAGE_PNG_VALUE);
        when(perfilService.buscarPorUsuario(7L)).thenReturn(Optional.of(perfil));

        ResponseEntity<byte[]> resposta = controller.foto(session);

        assertEquals(200, resposta.getStatusCode().value());
        assertEquals(MediaType.IMAGE_PNG, resposta.getHeaders().getContentType());
        assertArrayEquals(conteudo, resposta.getBody());
    }

    @Test
    void deveRetornarNomeDoProfissionalDaSessao() {
        session.setAttribute("usuarioNome", "psicologa");

        ResponseEntity<?> resposta = controller.sessao(session);

        assertEquals(200, resposta.getStatusCode().value());
        assertEquals("psicologa", ((java.util.Map<?, ?>) resposta.getBody()).get("nome"));
    }

    @Test
    void deveRecusarArquivoQueNaoSejaImagemValida() throws IOException {
        MockMultipartFile arquivo = new MockMultipartFile(
                "foto", "foto.png", MediaType.IMAGE_PNG_VALUE, "arquivo inválido".getBytes());

        ResponseEntity<String> resposta = controller.atualizarFoto(arquivo, session);

        assertEquals(400, resposta.getStatusCode().value());
        verifyNoInteractions(perfilService);
    }

    @Test
    void deveArmazenarImagemValidaDoUsuarioAutenticado() throws IOException {
        BufferedImage imagem = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        ImageIO.write(imagem, "png", saida);
        MockMultipartFile arquivo = new MockMultipartFile(
                "foto", "foto.png", MediaType.IMAGE_PNG_VALUE, saida.toByteArray());
        when(perfilService.atualizarFoto(eq(7L), any(byte[].class), eq(MediaType.IMAGE_PNG_VALUE)))
                .thenReturn(true);

        ResponseEntity<String> resposta = controller.atualizarFoto(arquivo, session);

        assertEquals(200, resposta.getStatusCode().value());
        verify(perfilService).atualizarFoto(eq(7L), any(byte[].class), eq(MediaType.IMAGE_PNG_VALUE));
    }
}
