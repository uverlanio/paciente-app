package com.pereira.jaala.prontuarios.service;

import com.pereira.jaala.prontuarios.dto.UsuarioDTO;
import com.pereira.jaala.prontuarios.model.Login;
import com.pereira.jaala.prontuarios.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ResetPasswordService {

    @Autowired
    LoginRepository loginRepository;

    public ResetPasswordService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Transactional
    public Login findUsuarioWithoutSenha(UsuarioDTO usuarioDTO){

        Login reset = new Login();

        if(Objects.nonNull(usuarioDTO.getNovaSenha())){
            reset = loginRepository.findByNome(usuarioDTO.getNome());
            if(Objects.nonNull(reset))
                loginRepository.updateNewPassword(reset.getNome(), usuarioDTO.getNovaSenha());
        }

        return reset;
    }
}
