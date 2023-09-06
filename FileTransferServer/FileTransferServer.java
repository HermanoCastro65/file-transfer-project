import java.io.*;
import java.net.*;

public class FileTransferServer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Error: try java FileTransServer <port>");
            System.exit(1);
        }

        final int PORT = Integer.parseInt(args[0]); // Argumento que recebe a porta que o servidor irá utilizar

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            
            System.out.println("Server connected on port: " + PORT);
            System.out.println("Waiting for client...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection established with" + clientSocket.getInetAddress());

                // Receber o nome do arquivo do cliente
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String fileName = in.readLine();
                System.out.println("Receiving file: " + fileName);

                // Abrir o arquivo para escrita no destino
                File file = new File(fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

                // Iniciar a contagem de tempo
                long startTime = System.nanoTime();

                // Receber e gravar os dados do arquivo
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = clientSocket.getInputStream().read(buffer)) != -1) {
                    bufferedOutputStream.write(buffer, 0, bytesRead);
                }

                // Calcular a taxa de transferência
                long endTime = System.nanoTime();
                double transferTime = (endTime - startTime) / 1e9; // segundos
                long fileSize = file.length(); // bytes
                double transferRateMbps = (fileSize / (1024 * 1024)) / transferTime;
                System.out.printf("Transfer rate: %.2f Mbps%n", transferRateMbps);

                // Fechar conexões e arquivos
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
