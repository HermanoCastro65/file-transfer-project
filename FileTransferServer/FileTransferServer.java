import java.io.*;
import java.net.*;

public class FileTransferServer {
  public static void main(String[] args) {
    // Verifica se um argumento (número da porta) foi fornecido ao iniciar o servidor
    if (args.length != 1) {
      System.err.println("Error: try java FileTransServer <port>");
      System.exit(1);
    }

    // Obtém o número da porta a partir do primeiro argumento fornecido na linha de comando
    final int PORT = Integer.parseInt(args[0]);

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      // Exibe a porta em que o servidor está conectado
      System.out.println("Server connected on port: " + PORT);
      System.out.println("Waiting for client...");

      while (true) {
        // Aguarda e aceita conexões de clientes
        Socket clientSocket = serverSocket.accept();
        System.out.println("Connection established with " + clientSocket.getInetAddress());

        // Recebe o nome do arquivo enviado pelo cliente
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String fileName = in.readLine();
        System.out.println("Receiving file: " + fileName);

        // Abre o arquivo para escrita no destino
        File file = new File(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        // Inicia a contagem de tempo para medir a taxa de transferência
        long startTime = System.nanoTime();

        // Define o tamanho máximo do buffer para receber os dados do arquivo
        final int filesize = 6022386;
        byte[] buffer = new byte[filesize];
        int bytesRead;

        // Recebe e escreve os dados do arquivo no destino
        while ((bytesRead = clientSocket.getInputStream().read(buffer)) != -1) {
          bufferedOutputStream.write(buffer, 0, bytesRead);
        }

        // Calcula a taxa de transferência em Mbps
        long endTime = System.nanoTime();
        double transferTime = (endTime - startTime) / 1e9; // segundos
        long fileSize = file.length(); // bytes
        double transferRateMbps = (fileSize / (1024 * 1024)) / transferTime;
        System.out.printf("Transfer rate: %.2f Mbps%n", transferRateMbps);

        // Fecha conexões e arquivos após a transferência
        bufferedOutputStream.close();
        fileOutputStream.close();
        in.close();
        clientSocket.close();

        System.out.println("Transfer complete. File saved as: " + fileName);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
