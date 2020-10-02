package com.example.danielfigueroa_practica6.model;

import java.util.UUID;

public class Usuario {

    private String nombreusuario;
    private String contrasenausuario;


    public Usuario (String nombreusuario, String contrasenausuario) {
        this.nombreusuario = nombreusuario;
        this.contrasenausuario = contrasenausuario;
    }

    public Usuario(){

    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getContrasenausuario() {
        return contrasenausuario;
    }

    public void setContrasenausuario(String contrasenausuario) {
        this.contrasenausuario = contrasenausuario;
    }
}
