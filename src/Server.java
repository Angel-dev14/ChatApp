import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {

    static public Set<WorkerThread> clients;
    private static int clientId = 0;
    private ServerSocket serverSocket;

    public Server(int port) {
        clients = new HashSet<WorkerThread>();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started...");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("Request received: " + client);

                WorkerThread thread = new WorkerThread(client, ++clientId);

                clients.add(thread);

                thread.start();

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Server(5000).start();
    }

}
