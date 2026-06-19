package com.practica.historias.dominio.modelo;

import java.util.List;

public class PaginaResultado<T> {
    private List<T> contenido;
    private long totalElements;
    private int totalPages;
    private int currentPage;

    public PaginaResultado(List<T> contenido, long totalElements, int totalPages, int currentPage) {
        this.contenido = contenido;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    // Getters
    public List<T> getContenido() { return contenido; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
    public int getCurrentPage() { return currentPage; }
}