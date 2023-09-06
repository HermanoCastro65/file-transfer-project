import java.io.*;
import java.net.*;

public class FileTransferClient {
  public static void main(String[] args) {
    if (args.length != 3) {
      System.err.println("Error: try java FileTransferClient <port> <server_ip> <file.extension>");
      System.exit(1);
    }

    final int SERVER_PORT = Integer.parseInt(args[0]); // Argumento que recebe porta usada no servidor
    final String SERVER_IP = args[1]; // Argumento que recebe o endereço IP do servidor

    String fileName = args[2]; // Argumento que recebe o nome do arquivo

    try (Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT); PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); FileInputStream fileInputStream = new FileInputStream(fileName); BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

      // Enviar o nome do arquivo ao servidor
      out.println(fileName);

      // Enviar os dados do arquivo ao servidor
      byte[] buffer = new byte[1024];
      int bytesRead;
      while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
        clientSocket.getOutputStream().write(buffer, 0, bytesRead);
      }

      System.out.println("File uploaded successfully: " + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}