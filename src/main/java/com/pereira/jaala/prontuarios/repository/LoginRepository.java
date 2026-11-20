package com.pereira.jaala.prontuarios.repository;

import com.pereira.jaala.prontuarios.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {

    Login findByNomeAndSenha(String usuario, String senha);

    Login findByNome(String usuario);

    @Modifying
    @Query("update Login login set login.senha = :novaSenha where login.nome = :nome")
    int updateNewPassword(@Param("nome") String nome, @Param("novaSenha") String novaSenha);
}
