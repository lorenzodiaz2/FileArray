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

    double[][] array = new double[2][32];

    for (int i = 1; i <= 32; i++) {
      FileArray otherFileArray = new GZIPFileArray(args[0], i);
      array[0][i - 1] = i;
      double n = (otherFileArray.getSize() / (4 + 4 * i)) * 100 - 100;
      array[1][i - 1] = n;
      System.out.printf("%s%d%s%s%s%.2f%s%n", "grandezza = ", i, ", percentuale piu' ", (n >= 0 ? "grande" : "piccolo"),
        " rispetto a FileArray = ", (n >= 0 ? array[1][i - 1] : array[1][i - 1] * (-1)), " %");
    }
  }
}