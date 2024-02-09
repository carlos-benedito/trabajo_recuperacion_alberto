package main;

public class Cita {
    private String centro;
    private String especialidad;
    private String doctor;
    private String fecha;
    private String hora;

    public Cita(String[] datos) {
        centro = datos[0];
        especialidad = datos[1];
        doctor = datos[2];
        fecha = datos[3];
        hora = datos[4];
    }

    // Getters
    public String getCentro() {
        return centro;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }
}


