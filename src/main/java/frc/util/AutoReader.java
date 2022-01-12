package frc.util;

import edu.wpi.first.wpilibj.*;
import java.io.File;
import java.util.Scanner;

public class AutoReader {
    public static void main(String[] args) throws Exception {
        // pass the path to the file as a parameter | Replace this with the dir on the rio
        File file = new File("C:\\Users\\harry\\IdeaProjects\\2022-Robot\\src\\main\\java\\frc\\util\\auto.txt");
        File file1 = new File(String.valueOf(Filesystem.getOperatingDirectory()) + "\\auto.txt");
        Scanner sc = new Scanner(file1);

        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
    }
}
