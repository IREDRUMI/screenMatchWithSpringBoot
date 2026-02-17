package com.aluracursos.screenmatch.model;

public enum Categoria {
    ACCION ("Action"),
    COMEDIA ("Comedy"),
    ROMANCE ("Romance"),
    DRAMA ("Drama"),
    CRIMEN ("Crime");

    private String categoriaOmdb;
    Categoria (String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
    }
}
