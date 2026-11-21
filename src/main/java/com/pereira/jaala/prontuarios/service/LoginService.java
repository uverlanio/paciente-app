package com.pereira.jaala.prontuarios.service;

import com.pereira.jaala.prontuarios.dto.UsuarioDTO;
import com.pereira.jaala.prontuarios.model.Login;
import com.pereira.jaala.prontuarios.repository.LoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Transactional
    public Login findUsuario(UsuarioDTO usuarioDTO){

        log.debug("Finding user with name: {}", usuarioDTO.getNome());

        Login logged = loginRepository.findByNomeAndSenha(usuarioDTO.getNome(), usuarioDTO.getSenha());
        Optional.ofNullable(logged)
                .ifPresent(usuario -> log.debug("User found: {}", usuario.getNome()));

        return logged;
    }

}
