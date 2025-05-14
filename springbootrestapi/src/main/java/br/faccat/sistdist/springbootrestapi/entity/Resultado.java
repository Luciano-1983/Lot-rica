package br.faccat.sistdist.springbootrestapi.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numeroSorteado;

    @OneToMany
    private List<Aposta> acertadores = new ArrayList<>();

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumeroSorteado() {
        return numeroSorteado;
    }

    public void setNumeroSorteado(int numeroSorteado) {
        this.numeroSorteado = numeroSorteado;
    }

    public List<Aposta> getAcertadores() {
        return acertadores;
    }

    public void setAcertadores(List<Aposta> acertadores) {
        this.acertadores = acertadores;
    }
}
