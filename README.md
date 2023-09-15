# Transferência de Arquivos - Servidor e Cliente

Este é um conjunto de programas em Java para transferência de arquivos entre hospedeiros na Internet usando TCP. O conjunto consiste em um servidor que aceita conexões de clientes e um cliente que envia arquivos para o servidor.

## Pré-requisitos
* JDK (Java Development Kit) instalado no sistema.
* Git instalado

## Instalação

```bash
git clone https://github.com/HermanoCastro65/file-transfer-project.git
```

## Compilação e Execução

### Servidor

1. Abra um terminal ou prompt de comando.
2. Navegue até o diretório onde o arquivo `FileTransferServer.java` está localizado.
3. Compile o servidor com o seguinte comando:

   ```bash
   javac FileTransferServer.java
   ```
4. Execute o servidor com o seguinte comando:


    ```bash
    java FileTransferServer <port>
    ```
    Substitua <port> pela porta desejada (por exemplo, 12345).

### Cliente

1. Abra outro terminal ou prompt de comando.
2. Navegue até o diretório onde o arquivo `FileTransferClient.java` está localizado.
3. Compile o cliente com o seguinte comando:

   ```bash
   javac FileTransferClient.java
   ```
4. Execute o cliente com o seguinte comando, fornecendo a porta do servidor, o endereço IP do servidor e o nome do arquivo a ser transferido:
    ```bash
    java FileTransferClient <port> <server_ip> <file.extension>
    ```
    Substitua <port> pela porta do servidor, <server_ip> pelo endereço IP do servidor e <file.extension> pelo nome do arquivo a ser transferido.

## Exemplo de Uso

### Servidor

```bash
$ java FileTransferServer 12345
Server connected on port: 12345
Waiting for client...
Connection established with 192.168.0.100
Receiving file: exemplo.txt
Transfer rate: 10.20 Mbps
Transfer complete. File saved as: exemplo.txt
```

### Cliente

```bash
$ java FileTransferClient 12345 192.168.0.100 exemplo.txt
File uploaded successfully: exemplo.txt
```
Certifique-se de substituir as informações relevantes, como porta, endereço IP e nome do arquivo, de acordo com a sua configuração.