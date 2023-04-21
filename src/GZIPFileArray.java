import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPFileArray extends FileArray {

  public GZIPFileArray(String filePathName) {
    super(filePathName);
  }

  public GZIPFileArray(String filePathName, int n) throws IOException {
    super(filePathName, n);
  }

  @Override
  protected void write(int[] array) throws IOException {
    write(array, new GZIPOutputStream(new DataOutputStream(new FileOutputStream(filePathName))));
  }

  @Override
  protected int[] read() throws IOException {
    return read(new GZIPInputStream(new DataInputStream(new FileInputStream(filePathName))));
  }
}
