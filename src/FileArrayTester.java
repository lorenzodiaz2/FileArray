import java.io.File;
import java.io.IOException;
import java.util.Random;

public class FileArrayTester {
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.printf("%s%n%s", "No argument passed", "Program finished");
      return;
    }

    File file = new File(args[0]);
    FileArray fileArray;

    if (file.exists() && file.getName().endsWith(".zip")) {
      fileArray = new GZIPFileArray(args[0]);
    } else if (!file.exists() && file.getName().endsWith(".zip")) {
      Random random = new Random();
      fileArray = new GZIPFileArray(args[0], random.nextInt(32) + 1);
    } else if (file.exists()) {
      fileArray = new FileArray(args[0]);
    } else {
      Random random = new Random();
      fileArray = new FileArray(args[0], random.nextInt(32) + 1);
    }

    for (String arg : args) {
      switch (arg) {
        case "p" -> fileArray.print();
        case "i" -> fileArray.incrementAll();
        default -> {
        }
      }
    }


  }
}