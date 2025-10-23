package com.pereira.jaala.prontuarios.repository;

import com.pereira.jaala.prontuarios.model.Login;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends MongoRepository<Login, String> {

    Login findByNomeAndSenha(String usuario, String senha);

    Login findByNome(String usuario);

    @Query(value = "{ 'nome' : ?0 }")
    @Update(update = "{ '$set' : { 'senha' : ?1 }}")
    Long updateNewPassword(String nome, String novaSenha);
}
