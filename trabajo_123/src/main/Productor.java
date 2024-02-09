package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.ArrayList;
import java.util.List;

public class Productor implements Runnable {
    private String inputFile;
    private BlockingQueue<Paciente> pacientesQueue;
    private static final String PACIENTES_FOLDER = "Pacientes";

    public Productor(String inputFile, BlockingQueue<Paciente> pacientesQueue) {
        this.inputFile = inputFile;
        this.pacientesQueue = pacientesQueue;
    }

    @Override
    public void run() {
        try {
            File pacientesDirectory = new File(PACIENTES_FOLDER);
            if (!pacientesDirectory.exists()) {
                pacientesDirectory.mkdir();
            }

            List<Paciente> pacientes = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                String line;
                Paciente paciente = null;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("#")) {
                        if (paciente != null) {
                            Cita cita = crearCita(line);
                            if (cita != null) {
                                paciente.agregarCita(cita);
                            }
                        } else {
                        }
                    } else {
                        if (paciente != null) {
                            pacientes.add(paciente);
                        }
                        paciente = crearPaciente(line);
                    }
                }
                if (paciente != null) {
                    pacientes.add(paciente);
                }
            }
            for (Paciente paciente : pacientes) {
                crearEstructuraPaciente(paciente);
                pacientesQueue.put(paciente);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Paciente crearPaciente(String line) {
        String[] datos = line.split(";");
        if (datos.length >= 6 && !line.startsWith("#")) {
            return new Paciente(datos);
        } else {
            System.err.println("Error datos del paciente");
            return null;
        }
    }

    private Cita crearCita(String line) {
        String[] datos = line.substring(1).split(";");
        if (datos.length >= 5) {
            return new Cita(datos);
        } else {
            System.err.println("Error datos de la cita");
            return null;
        }
    }
    
    private void crearEstructuraPaciente(Paciente paciente) {
        String idPaciente = String.format("%09d", paciente.getId());

        File pacienteFolder = new File(PACIENTES_FOLDER + File.separator + idPaciente);
        pacienteFolder.mkdir();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pacienteFolder.getAbsolutePath() + File.separator + "DatosPersonales.xml"))) {
            writer.write("<DatosPersonales>");
            writer.newLine();
            writer.write("\t<Nombre>" + paciente.getNombre() + "</Nombre>");
            writer.newLine();
            writer.write("\t<Apellido1>" + paciente.getApellido1() + "</Apellido1>");
            writer.newLine();
            writer.write("\t<Apellido2>" + paciente.getApellido2() + "</Apellido2>");
            writer.newLine();
            writer.write("\t<Nacimiento>" + paciente.getNacimiento() + "</Nacimiento>");
            writer.newLine();
            writer.write("\t<Localidad>" + paciente.getLocalidad() + "</Localidad>");
            writer.newLine();
            writer.write("</DatosPersonales>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pacienteFolder.getAbsolutePath() + File.separator + "Citas.xml"))) {
            writer.write("<HistorialCitas>");
            for (Cita cita : paciente.getHistorialCitas()) {
                writer.newLine();
                writer.write("\t<Cita>");
                writer.newLine();
                writer.write("\t\t<Centro>" + cita.getCentro() + "</Centro>");
                writer.newLine();
                writer.write("\t\t<Especialidad>" + cita.getEspecialidad() + "</Especialidad>");
                writer.newLine();
                writer.write("\t\t<Doctor>" + cita.getDoctor() + "</Doctor>");
                writer.newLine();
                writer.write("\t\t<Fecha>" + cita.getFecha() + "</Fecha>");
                writer.newLine();
                writer.write("\t\t<Hora>" + cita.getHora() + "</Hora>");
                writer.newLine();
                writer.write("\t</Cita>");
            }
            writer.newLine();
            writer.write("</HistorialCitas>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




