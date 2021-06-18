import javax.swing.*;
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
    ChatWindow chatWindow;

    public Client(String serverName, int port,String user) {
        try {
            this.socket = new Socket(serverName, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        chatWindow = new ChatWindow(user);
        JButton button = chatWindow.getSendButton();
        button.addActionListener(e -> new Send().start());
        new Receive().start();
    }

    class Send extends Thread {

        private Scanner scanner;
        public Send() {
            scanner = new Scanner(System.in);
        }

        @Override
        public void run() {
            sendMessage();
        }

        private boolean validateMessage(String message)
        {
            if((!message.contains(":") || message.split(":").length != 2) && !message.equals("logout"))
                return false;
            return true;
        }
        private void sendMessage(){


            String message = chatWindow.getMessageBox().getText();
            if(message.isEmpty() || !validateMessage(message))
                return;
            chatWindow.getMessageBox().setText(null);

            writer.println(message);
            writer.flush();

            displaySenderMessage(message);

        }
        private void displaySenderMessage(String message){

            chatWindow.addSendersMessage(message);

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
                        displayReceiverMessage(message);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }

        }
        private void displayReceiverMessage(String message){
            chatWindow.addReceiversMessage(message);
        }
    }
}
