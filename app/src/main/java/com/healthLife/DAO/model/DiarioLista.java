package com.healthLife.DAO.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table DIARIO_LISTA.
 */
public class DiarioLista {

    private Long id;
    private Long id_diario;
    private Long id_alimento;
    private Double porcion;

    public DiarioLista() {
    }

    public DiarioLista(Long id) {
        this.id = id;
    }

    public DiarioLista(Long id, Long id_diario, Long id_alimento, Double porcion) {
        this.id = id;
        this.id_diario = id_diario;
        this.id_alimento = id_alimento;
        this.porcion = porcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_diario() {
        return id_diario;
    }

    public void setId_diario(Long id_diario) {
        this.id_diario = id_diario;
    }

    public Long getId_alimento() {
        return id_alimento;
    }

    public void setId_alimento(Long id_alimento) {
        this.id_alimento = id_alimento;
    }

    public Double getPorcion() {
        return porcion;
    }

    public void setPorcion(Double porcion) {
        this.porcion = porcion;
    }

}