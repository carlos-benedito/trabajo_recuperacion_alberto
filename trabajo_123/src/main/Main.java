package main;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        String inputFile = "pacientes.txt";
        String outputFolder = "Pacientes";
        int bufferSize = 10;

        BlockingQueue<Paciente> pacientesQueue = new ArrayBlockingQueue<>(bufferSize);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Productor(inputFile, pacientesQueue));
        executorService.execute(new Consumidor(outputFolder, pacientesQueue));

        executorService.shutdown();
    }
}
