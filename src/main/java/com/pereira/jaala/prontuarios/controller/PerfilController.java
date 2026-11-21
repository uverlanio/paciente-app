package com.pereira.jaala.prontuarios.controller;

import com.pereira.jaala.prontuarios.model.PerfilProfissional;
import com.pereira.jaala.prontuarios.service.PerfilService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    private static final long TAMANHO_MAXIMO_FOTO = 5 * 1024 * 1024;
    private static final int DIMENSAO_MAXIMA_FOTO = 4096;

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping("/sessao")
    public ResponseEntity<Map<String, String>> sessao(HttpSession session) {
        Long usuarioId = usuarioAutenticado(session);
        Object nome = session.getAttribute("usuarioNome");
        if (usuarioId == null || !(nome instanceof String)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(Map.of("nome", (String) nome));
    }

    @GetMapping("/foto")
    public ResponseEntity<byte[]> foto(HttpSession session) {
        Long usuarioId = usuarioAutenticado(session);
        if (usuarioId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<PerfilProfissional> perfil = perfilService.buscarPorUsuario(usuarioId);
        if (perfil.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MediaType tipo = MediaType.parseMediaType(perfil.get().getTipoFoto());
        return ResponseEntity.ok()
                .contentType(tipo)
                .cacheControl(CacheControl.noCache())
                .body(perfil.get().getFoto());
    }

    @PostMapping(value = "/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> atualizarFoto(
            @RequestParam("foto") MultipartFile arquivo,
            HttpSession session) throws IOException {
        Long usuarioId = usuarioAutenticado(session);
        if (usuarioId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Faça login para alterar a foto.");
        }
        if (arquivo.isEmpty() || arquivo.getSize() > TAMANHO_MAXIMO_FOTO) {
            return ResponseEntity.badRequest().body("Selecione uma imagem de até 5 MB.");
        }

        byte[] conteudo = arquivo.getBytes();
        String tipo = validarImagem(conteudo);
        if (tipo == null) {
            return ResponseEntity.badRequest().body("Use uma imagem JPEG ou PNG válida, com até 4096 px.");
        }

        if (!perfilService.atualizarFoto(usuarioId, conteudo, tipo)) {
            session.invalidate();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("A sessão expirou. Entre novamente.");
        }

        return ResponseEntity.ok("Foto de perfil atualizada.");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.noContent().build();
    }

    private Long usuarioAutenticado(HttpSession session) {
        Object usuarioId = session.getAttribute("usuarioId");
        return usuarioId instanceof Long ? (Long) usuarioId : null;
    }

    private String validarImagem(byte[] conteudo) {
        try (ImageInputStream entrada = ImageIO.createImageInputStream(new ByteArrayInputStream(conteudo))) {
            if (entrada == null) {
                return null;
            }

            Iterator<ImageReader> leitores = ImageIO.getImageReaders(entrada);
            if (!leitores.hasNext()) {
                return null;
            }

            ImageReader leitor = leitores.next();
            try {
                leitor.setInput(entrada, true, true);
                String formato = leitor.getFormatName();
                int largura = leitor.getWidth(0);
                int altura = leitor.getHeight(0);
                if (largura <= 0 || altura <= 0
                        || largura > DIMENSAO_MAXIMA_FOTO || altura > DIMENSAO_MAXIMA_FOTO) {
                    return null;
                }

                BufferedImage imagem = leitor.read(0);
                if (imagem == null) {
                    return null;
                }
                if ("JPEG".equalsIgnoreCase(formato) || "JPG".equalsIgnoreCase(formato)) {
                    return MediaType.IMAGE_JPEG_VALUE;
                }
                if ("PNG".equalsIgnoreCase(formato)) {
                    return MediaType.IMAGE_PNG_VALUE;
                }
                return null;
            } finally {
                leitor.dispose();
            }
        } catch (IOException e) {
            return null;
        }
    }
}
