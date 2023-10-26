package org.cmendoza.mantenedor.models;

public class Categoria {
    private Long id;
    private String categoria;

    ///////////////////////////////////////////////////////////
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getCategoria(){
        return this.categoria;
    }
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }
}
