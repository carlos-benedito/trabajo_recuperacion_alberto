package main;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String nacimiento;
    private String localidad;
    private List<Cita> historialCitas;

    public Paciente(String[] datos) {
        this.id = Integer.parseInt(datos[0]);
        this.nombre = datos[1];
        this.apellido1 = datos[2];
        this.apellido2 = datos[3];
        this.nacimiento = datos[4];
        this.localidad = datos[5];
        this.historialCitas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public String getLocalidad() {
        return localidad;
    }

    public List<Cita> getHistorialCitas() {
        return historialCitas;
    }

    public void agregarCita(Cita cita) {
        historialCitas.add(cita);
    }
}

