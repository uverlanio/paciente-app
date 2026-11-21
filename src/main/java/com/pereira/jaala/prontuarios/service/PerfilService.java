package com.pereira.jaala.prontuarios.service;

import com.pereira.jaala.prontuarios.model.Login;
import com.pereira.jaala.prontuarios.model.PerfilProfissional;
import com.pereira.jaala.prontuarios.repository.LoginRepository;
import com.pereira.jaala.prontuarios.repository.PerfilProfissionalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PerfilService {

    private final LoginRepository loginRepository;
    private final PerfilProfissionalRepository perfilRepository;

    public PerfilService(LoginRepository loginRepository, PerfilProfissionalRepository perfilRepository) {
        this.loginRepository = loginRepository;
        this.perfilRepository = perfilRepository;
    }

    @Transactional(readOnly = true)
    public Optional<PerfilProfissional> buscarPorUsuario(Long usuarioId) {
        return perfilRepository.findById(usuarioId);
    }

    @Transactional
    public boolean atualizarFoto(Long usuarioId, byte[] foto, String tipoFoto) {
        Login usuario = loginRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            return false;
        }

        PerfilProfissional perfil = perfilRepository.findById(usuarioId)
                .orElseGet(() -> new PerfilProfissional(usuario, foto, tipoFoto));
        perfil.setFoto(foto);
        perfil.setTipoFoto(tipoFoto);
        perfilRepository.save(perfil);
        return true;
    }
}
