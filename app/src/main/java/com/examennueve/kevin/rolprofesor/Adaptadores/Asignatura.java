package com.examennueve.kevin.rolprofesor.Adaptadores;

/**
 * Created by kevin on 2/05/2017.
 */

public class Asignatura {

    private String id_asignatura;
    private String nombre_asignatura;


    public Asignatura(String id_asignatura, String nombre_asignatura) {
        this.id_asignatura = id_asignatura;
        this.nombre_asignatura = nombre_asignatura;
    }

    public Asignatura() {

    }

    public String getId_asignatura() {
        return id_asignatura;
    }

    public void setId_asignatura(String id_asignatura) {
        this.id_asignatura = id_asignatura;
    }

    public String getNombre_asignatura() {
        return nombre_asignatura;
    }

    public void setNombre_asignatura(String nombre_asignatura) {
        this.nombre_asignatura = nombre_asignatura;
    }
}
