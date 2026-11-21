package com.pereira.jaala.prontuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "perfil_profissional")
public class PerfilProfissional {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id")
    private Login usuario;

    @Lob
    @Column(name = "foto", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] foto;

    @Column(name = "tipo_foto", length = 32, nullable = false)
    private String tipoFoto;

    public PerfilProfissional(Login usuario, byte[] foto, String tipoFoto) {
        this.usuario = usuario;
        this.foto = foto;
        this.tipoFoto = tipoFoto;
    }
}
