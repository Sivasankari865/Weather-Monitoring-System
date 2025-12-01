import java.io.*;
import java.net.*;

public class WeatherClient {

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 9091;

        try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("--- Real-Time Weather Fetcher ---");
            System.out.print("Enter a city name (or type 'exit' to quit): ");

            String city;
            while ((city = consoleReader.readLine()) != null) {
                if ("exit".equalsIgnoreCase(city)) {
                    break;
                }

                try (Socket socket = new Socket(hostname, port)) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    out.println(city);
                    String serverResponse = in.readLine();
                    System.out.println(">>> SERVER RESPONSE: " + serverResponse);

                } catch (UnknownHostException ex) {
                    System.err.println("Server not found: " + ex.getMessage());
                } catch (IOException ex) {
                    System.err.println("I/O error: " + ex.getMessage());
                }

                System.out.print("\nEnter another city name (or type 'exit' to quit): ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
