package com.examennueve.kevin.rolprofesor.Adaptadores;

/**
 * Created by kevin on 12/05/2017.
 */

public class Tareas {

    private String idtarea;
    private String idasignatura;
    private String idusuario_prof;
    private String idusuario_alumn;
    private String nombre;
    private String nota;
//id,idmateriaSeleccionada,idestudianteSeleccionado,nombreTarea,nota
    public Tareas(){

    }

    public Tareas(String idtarea, String idasignatura, String idusuario_prof, String nombre, String nota) {
        this.idtarea = idtarea;
        this.idasignatura = idasignatura;
        this.idusuario_prof = idusuario_prof;
        this.nombre = nombre;
        this.nota = nota;
    }

    public Tareas(String idtarea, String idasignatura, String idusuario_prof, String idusuario_alumn, String nombre, String nota) {
        this.idtarea = idtarea;
        this.idasignatura = idasignatura;
        this.idusuario_prof = idusuario_prof;
        this.idusuario_alumn = idusuario_alumn;
        this.nombre = nombre;
        this.nota = nota;
    }

    public String getIdtarea() {
        return idtarea;
    }

    public void setIdtarea(String idtarea) {
        this.idtarea = idtarea;
    }

    public String getIdasignatura() {
        return idasignatura;
    }

    public void setIdasignatura(String idasignatura) {
        this.idasignatura = idasignatura;
    }

    public String getIdusuario_prof() {
        return idusuario_prof;
    }

    public void setIdusuario_prof(String idusuario_prof) {
        this.idusuario_prof = idusuario_prof;
    }

    public String getIdusuario_alumn() {
        return idusuario_alumn;
    }

    public void setIdusuario_alumn(String idusuario_alumn) {
        this.idusuario_alumn = idusuario_alumn;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
