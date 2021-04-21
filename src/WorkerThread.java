import java.io.*;
import java.net.Socket;

public class WorkerThread extends Thread {

    private Socket client;
    private int clientID;
    private boolean active;

    private BufferedReader reader;
    private PrintWriter writer;

    public WorkerThread(Socket client, int clientID) {
        System.out.println("passed clientID " + clientID);
        this.client = client;
        this.clientID = clientID;
        active = true;
        try {
            this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {
            try {

                String line = reader.readLine();
                if(line.equals("logout")){
                    active = false;
                    writer.println("Successfully logged out, you may close the app");
                    writer.flush();
                    client.close();
                    break;
                }

                String[] parts = line.split(":");

                int recipientID = Integer.parseInt(parts[0]);

                String message = parts[1];

                for (WorkerThread clients : Server.clients) {
                    if ((clients.clientID == recipientID) && clients.active) {
                        System.out.println("Found the client: " + recipientID);
                        System.out.println("Message to send: " + message);
                        clients.writer.println(String.format("%d:%s", clientID, message));
                        clients.writer.flush();
                        break;
                    }
                }

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

    }

}
