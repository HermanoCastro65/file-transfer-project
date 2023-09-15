# Explicação do Código do Cliente de Transferência de Arquivos

Este arquivo fornece uma explicação detalhada do código do cliente em Java para transferência de arquivos entre hospedeiros na Internet. O cliente se conecta a um servidor remoto, envia um arquivo especificado para o servidor e exibe uma mensagem de sucesso após a conclusão da operação.

## Estrutura do Código

O código é estruturado da seguinte forma:

```java
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
```
## Explicação do Código
Aqui está uma explicação do código do cliente:

* O código começa com a importação das bibliotecas necessárias para a comunicação de rede e manipulação de arquivos.
* O método main é o ponto de entrada do programa do cliente.
* Ele verifica se três argumentos (porta, endereço IP do servidor e nome do arquivo) foram fornecidos na linha de comando ao iniciar o cliente.
* As informações da porta, endereço IP do servidor e nome do arquivo são obtidas a partir dos argumentos fornecidos.
* Um bloco try inicia a configuração do cliente, incluindo a criação de um socket do cliente para se conectar ao servidor especificado.
* O cliente envia o nome do arquivo ao servidor, que é lido pelo servidor antes dos dados do arquivo.
* É definido um tamanho máximo para o buffer usado para receber os dados do arquivo.
* O cliente lê o arquivo especificado e envia os dados do arquivo para o servidor em um loop.
* Após a conclusão da transferência, o cliente exibe uma mensagem indicando que o arquivo foi enviado com sucesso.
* Qualquer exceção que ocorra durante a execução é tratada e as informações de erro são impressas.

Este código permite que o cliente envie arquivos para o servidor, e é importante garantir que os argumentos fornecidos na linha de comando estejam corretos, pois eles especificam a porta, o endereço IP do servidor e o nome do arquivo a ser transferido.