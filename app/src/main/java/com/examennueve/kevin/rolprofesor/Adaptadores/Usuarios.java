package com.examennueve.kevin.rolprofesor.Adaptadores;

/**
 * Created by kevin on 12/05/2017.
 */

public class Usuarios {

    private String id_usuario;
    private String username;
    private String nombre;
    private String password;
    private String rol;

    public Usuarios(String id_usuario, String username, String nombre, String password, String rol) {
        this.username = username;
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }

    public Usuarios() {

    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
