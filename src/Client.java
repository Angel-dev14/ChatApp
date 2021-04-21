import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client(String serverName, int port) {
        try {
            this.socket = new Socket(serverName, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        new Send().start();
        new Receive().start();
    }

    class Send extends Thread {

        public Send() {
        }

        @Override
        public void run() {

            Scanner scanner = null;
            while (true) {
                try {
                    writer = new PrintWriter(socket.getOutputStream());
                    scanner = new Scanner(System.in);
                    String message = scanner.nextLine();

                    writer.println(message);
                    writer.flush();

                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    class Receive extends Thread {

        public Receive() {
        }

        @Override
        public void run() {

            while (true) {
                try {
                    String message = null;
                    while ((message = reader.readLine()) != null)
                        System.out.println(message);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }

        }
    }


}
