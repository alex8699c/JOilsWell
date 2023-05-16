package utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordManager {
	
	//PATH del file
    private static final String RECORDS_FILE = "records" + File.separator + "records.txt";
    
    //Aggiorna un record
    public static void updateRecord(int dif, int lvl, int record) throws IOException {
        //Legge tutti i record dal file e li memorizza in una lista
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RECORDS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        //Cerca la riga corrispondente ai valori di dif e lvl e la sostituisce
        boolean updated = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length != 3) {
                System.out.println("Errore nel formato della riga: " + lines.get(i));
                continue;
            }
            int currDif = Integer.parseInt(parts[0]);
            int currLvl = Integer.parseInt(parts[1]);
            if (currDif == dif && currLvl == lvl) {
                lines.set(i, dif + "," + lvl + "," + record);
                updated = true;
            }
        }

        //Se non è stata trovata alcuna riga corrispondente, aggiunge una nuova riga
        if (!updated) {
            lines.add(dif + "," + lvl + "," + record);
        }

        //Riscrive tutti i record nel file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECORDS_FILE))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    //Restituisce un record
    public static int getRecord(int dif, int lvl) throws IOException {
        //Verifica se il file esiste
        File file = new File(RECORDS_FILE);
        if (!file.exists()) {
            System.out.println("Il file " + RECORDS_FILE + " non esiste.");
            return 0;
        }

        //Cerca il record corrispondente ai valori di dif e lvl
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int maxRecord = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    System.out.println("Errore nel formato della riga: " + line);
                    continue;
                }
                int currDif = Integer.parseInt(parts[0]);
                int currLvl = Integer.parseInt(parts[1]);
                int currRecord = Integer.parseInt(parts[2]);
                if (currDif == dif && currLvl == lvl) {
                    maxRecord = currRecord;
                }
            }
            return maxRecord;
        }
    }
}