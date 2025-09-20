package com.pereira.jaala.prontuarios.repository;

import com.pereira.jaala.prontuarios.model.Login;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends MongoRepository<Login, String> {

    Login findByNomeAndSenha(String usuario, String senha);
}
