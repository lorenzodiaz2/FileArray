import java.io.*;
import java.util.Random;

import static java.nio.file.Files.size;

public class FileArray {
  protected final String filePathName;

  public FileArray(String filePathName) {
    this.filePathName = filePathName;
  }

  public FileArray(String filePathName, int n) throws IOException {
    this.filePathName = filePathName;
    int maxValue = 1025;
    int[] array = generateRandomArray(n, maxValue);
    write(array);
  }

  protected void write(int[] array) throws IOException {
    write(array, new BufferedOutputStream(new FileOutputStream(filePathName)));
  }

  protected static void write(int[] array, OutputStream outputStream) throws IOException {
    DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(outputStream));
    dataOutputStream.writeInt(array.length);

    for (int values : array) {
      dataOutputStream.writeInt(values);
    }
    dataOutputStream.close();
  }

  protected int[] read() throws IOException {
    return read(new BufferedInputStream(new FileInputStream(filePathName)));
  }

  protected static int[] read(InputStream inputStream) throws IOException {
    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
    int[] array = new int[dataInputStream.readInt()];

    for (int i = 0; i < array.length; i++) {
      array[i] = dataInputStream.readInt();
    }
    dataInputStream.close();
    return array;
  }

  protected void incrementAll() throws IOException {
    int[] array = read();
    for (int i = 0; i < array.length; i++) {
      array[i]++;
    }
    write(array);
  }

  protected void print() throws IOException {
    int[] array = read();
    int elementsPerRow = 5;
    int n = array.length % elementsPerRow == 0 ? array.length / elementsPerRow : array.length / elementsPerRow + 1;
    int formatForIndexes = (int) Math.floor(Math.log10(array.length) + 1);
    int formatForElements = findMaxValue(array) != 0 ? (int) Math.floor(Math.log10(findMaxValue(array)) + 1) : 1;

    for (int i = 0; i < n; i++) {
      if (i < n - 1) {
        System.out.printf("[%0" + formatForIndexes + "d-%0" + formatForIndexes + "d]", i * elementsPerRow, i * elementsPerRow + (elementsPerRow - 1));
        for (int j = i * elementsPerRow; j < i * elementsPerRow + elementsPerRow; j++) {
          System.out.printf(" %" + formatForElements + "d", array[j]);
        }
        System.out.println();
      } else {
        System.out.printf("[%0" + formatForIndexes + "d-%0" + formatForIndexes + "d]", i * elementsPerRow, array.length - 1);

        for (int j = i * elementsPerRow; j < i * elementsPerRow + (array.length % elementsPerRow == 0 ? elementsPerRow : array.length % elementsPerRow); j++) {
          System.out.printf(" %" + formatForElements + "d", array[j]);
        }
      }
    }
    System.out.println();
  }

  private static int findMaxValue(int... array) {
    int max = array[0];
    for (int i = 1; i < array.length; i++) {
      if (array[i] > max) {
        max = array[i];
      }
    }
    return max;
  }

  private static int[] generateRandomArray(int n, int bound) {
    int[] array = new int[n];
    Random random = new Random();
    for (int i = 0; i < n; i++) {
      array[i] = random.nextInt(bound);
    }
    return array;
  }

  protected double getSize() throws IOException {
    File file = new File(filePathName);
    return (double) size(file.toPath());
  }
}