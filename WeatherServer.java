import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class WeatherServer {
    private static final String API_KEY = "YOUR_API_KEY_HERE"; 
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    public static void main(String[] args) {
        int port = 9091; 

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Weather Server is running and listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String cityName = in.readLine();
                System.out.println("Received request for city: " + cityName);

                String weatherData = fetchWeatherData(cityName);

                out.println(weatherData);
                System.out.println("Sent weather data to client.");

                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String fetchWeatherData(String city) {
        if (city == null || city.trim().isEmpty()) {
            return "Error: City name cannot be empty.";
        }
        
        if ("YOUR_API_KEY_HERE".equals(API_KEY)) {
            return "Error: API Key is not configured. Please set it in WeatherServer.java";
        }

        try {
            String formattedUrl = String.format(API_URL, URLEncoder.encode(city, StandardCharsets.UTF_8.toString()), API_KEY);
            URL url = new URL(formattedUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) { 
                BufferedReader apiReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = apiReader.readLine()) != null) {
                    response.append(line);
                }
                apiReader.close();
                return parseSimpleJson(response.toString());

            } else if (responseCode == 404) {
                return "Error: City not found. Please check spelling.";
            } else {
                return "Error: Could not retrieve data. Response code: " + responseCode;
            }

        } catch (IOException e) {
            return "Error: Network problem. " + e.getMessage();
        }
    }

    private static String parseSimpleJson(String json) {
        try {
            String temp = json.split("\"temp\":")[1].split(",")[0];
            String humidity = json.split("\"humidity\":")[1].split("}")[0];
            String description = json.split("\"description\":\"")[1].split("\"")[0];
            String cityName = json.split("\"name\":\"")[1].split("\"")[0];

            return String.format(
                "Weather for %s: Temp: %s°C, Humidity: %s%%, Cond: %s",
                cityName, temp, humidity, description
            );
        } catch (Exception e) {
            return "Error: Failed to parse weather data.";
        }
    }
}