import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StopOverManagement {
    String filename = "/home/pierfrancescoblancato/IdeaProjects/Programmazione_1/02_Parking/src/data/StopOver";

    public void saveToFile(List<StopOver> dataToSave) {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            System.out.println("Saving " + dataToSave.size() + " parking...");

            for (StopOver so : dataToSave) {
                String line = so.toString();

                writer.write(line);
                writer.newLine();
            }
            writer.close();
            System.out.println("Saved successfully!");
        } catch (IOException e) {
            System.out.println("Error while saving: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {
                String clean = line.replace("Stopover{", "").replace("}", "");

                String[] fields = clean.split(";");

                System.out.println("{");

                for (int i = 0; i < fields.length; i++) {
                    String field = fields[i].trim();
                    if (field.isEmpty()) continue;

                    String[] parts = field.split(":", 2);

                    if (parts.length == 2) {
                        String key = parts[0].replace(",", "").trim();
                        String value = parts[1].trim();

                        System.out.print("  \"" + key + "\": \"" + value + "\"");

                        if (i < fields.length - 1 && !fields[i+1].trim().isEmpty()) {
                            System.out.print(",");
                        }
                        System.out.println();
                    }
                }
                System.out.println("},");
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Errore durante la lettura o formattazione:");
            e.printStackTrace();
        }
    }
}
