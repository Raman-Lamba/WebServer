// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.io.PrintWriter;
// import java.net.ServerSocket;
// import java.net.Socket;
// import java.util.function.Consumer;

// public class Server {

//     public Consumer<Socket> getComsumer(String filePath) {
//         // return new Consumer<Socket>() {
//         // @Override
//         // public void accept(Socket clientSocket){
//         // try{
//         // PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
//         // toClient.println("Hello from the server");
//         // toClient.close();
//         // clientSocket.close();
//         // }catch(IOException ex){
//         // ex.printStackTrace();
//         // }
//         // }
//         // };
//         return (clientSocket) -> {
//             try (
//                     PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
//                     BufferedReader fileReader = new BufferedReader(new FileReader(filePath));)

//             {
//                 String line;
//                 while ((line = fileReader.readLine()) != null) {
//                     toClient.println(line);
//                 }
//             } catch (IOException ex) {
//                 ex.printStackTrace();
//             } finally {
//                 try {
//                     clientSocket.close();
//                 } catch (IOException ex) {
//                     ex.printStackTrace();
//                 }
//             }
//         };
//     }

//     public static void main(String[] args) {
//         int port = 8010;
//         String filePath = "/Users/ramanlamba/Documents/sample/test.txt";
//         Server server = new Server();
//         try {
//             ServerSocket serverSocket = new ServerSocket(port);
//             serverSocket.setSoTimeout(70000);
//             System.out.println("Server is listening on port" + port);
//             while (true) {
//                 Socket acceptedSocket = serverSocket.accept();
//                 Thread thread = new Thread(() -> server.getComsumer(filePath).accept(acceptedSocket));
//                 thread.start();
//             }
//         } catch (IOException ex) {
//             ex.printStackTrace();
//         }
//     }
// }
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
                toSocket.println("Hello from server " + clientSocket.getInetAddress());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }
    
    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
        
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                
                // Create and start a new thread for each client
                Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
                thread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}