import java.io.*;
import java.util.Scanner;

public class Task {
    public static void main(String[] args) {
        System.out.println("Введите путь к файлу");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.next();
        scanner.close();

        String filePathValidNumbers = "ValidNumbers.txt";
        String filePathInvalidNumbers = "InvalidNumbers.txt";

        try {
            new File(filePathValidNumbers).createNewFile();
            new File(filePathInvalidNumbers).createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try (BufferedReader file = new BufferedReader(new FileReader(filePath));
             BufferedWriter fileValid = new BufferedWriter(new FileWriter(filePathValidNumbers));
             BufferedWriter fileInvalid = new BufferedWriter(new FileWriter(filePathInvalidNumbers))
        ) {
            String line = file.readLine();
            while (line != null && !line.isBlank()) {
                String[] arrayValidity = checkValidity(line);
                if (arrayValidity[0].equals("invalid")) {
                    fileInvalid.write(line.trim() + " " + arrayValidity[1] + System.getProperty("line.separator"));
                } else {
                    fileValid.write(line.trim() + System.getProperty("line.separator"));
                }
                line = file.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String[] checkValidity(String line) {

        String valid = "valid";
        String reasonInvalidity = "";
        if (line.length() > 15) {
            valid = "invalid";
            reasonInvalidity = reasonInvalidity + " длина номера больше 15 символов";
        }
        if (!(line.startsWith("docnum") || line.startsWith("kontract"))) {
            valid = "invalid";
            reasonInvalidity = reasonInvalidity + " начало номера не соответсвует шаблону(\"docnum\" или \"kontract\")";
        }
        if (!line.replaceAll("[A-Z a-z 0-9]", " ").trim().isBlank()) {
            valid = "invalid";
            reasonInvalidity = reasonInvalidity + " номер содержит спецсимволы";
        }
        return new String[]{valid, reasonInvalidity};
    }
}
