package com.pereira.jaala.prontuarios.service;

import com.pereira.jaala.prontuarios.dto.UsuarioDTO;
import com.pereira.jaala.prontuarios.model.Login;
import com.pereira.jaala.prontuarios.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Login findUsuario(UsuarioDTO usuarioDTO){
        return loginRepository.findByNomeAndSenha(usuarioDTO.getNome(), usuarioDTO.getSenha());
    }
}
