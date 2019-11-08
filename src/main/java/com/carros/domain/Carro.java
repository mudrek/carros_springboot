package com.carros.domain;

import com.carros.domain.dto.CarroDTO;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Entity
@Data
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String tipo;
    private String descricao;

    @Column(name = "url_foto")
    private String urlFoto;

    @Column(name = "url_video")
    private String urlVideo;

    private String latitude;
    private String longitude;

    public Carro() {

    }

    public Carro(CarroDTO c) {
        this.id = c.getId();
        this.nome = c.getNome();
        this.tipo = c.getTipo();
    }

}
