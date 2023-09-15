import java.io.*;
import java.net.*;

public class FileTransferClient {
  public static void main(String[] args) {
    // Verifica se três argumentos (porta, endereço IP do servidor e nome do arquivo) foram fornecidos ao iniciar o cliente
    if (args.length != 3) {
      System.err.println("Error: try java FileTransferClient <port> <server_ip> <file.extension>");
      System.exit(1);
    }

    // Obtém a porta do servidor a partir do primeiro argumento fornecido na linha de comando
    final int SERVER_PORT = Integer.parseInt(args[0]); 

    // Obtém o endereço IP do servidor a partir do segundo argumento fornecido na linha de comando
    final String SERVER_IP = args[1]; 

    // Obtém o nome do arquivo a ser transferido a partir do terceiro argumento fornecido na linha de comando
    String fileName = args[2]; 

    try (Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);
         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
         FileInputStream fileInputStream = new FileInputStream(fileName);
         BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

      // Enviar o nome do arquivo ao servidor
      out.println(fileName);

      // Enviar os dados do arquivo ao servidor
      final int filesize = 6022386; // Tamanho máximo do buffer para receber os dados do arquivo
      byte[] buffer = new byte[filesize];
      int bytesRead;
      while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
        clientSocket.getOutputStream().write(buffer, 0, bytesRead);
      }

      // Exibe uma mensagem indicando que o arquivo foi enviado com sucesso
      System.out.println("File uploaded successfully: " + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
