import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class User1 {


    public static void main(String[] args) throws IOException {

        new Client("localhost", 5000,"User 1");

    }
}
