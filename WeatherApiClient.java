import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class WeatherApiClient {

    // Replace with your OpenWeatherMap API Key
    private static final String API_KEY = "99697f1adaa6a0ecec90e2d33d069043";

    public static void main(String[] args) {
        String city = "Delhi";
        getWeatherData(city);
    }

    public static void getWeatherData(String city) {
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q="
                    + city + "&appid=" + API_KEY + "&units=metric";

            // Create HTTP Client
            HttpClient client = HttpClient.newHttpClient();

            // Create HTTP GET Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            // Send Request and Get Response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
System.out.println("Raw Response:\n" + response.body());
            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(response.body());

            String cityName = jsonResponse.getString("name");
            JSONObject main = jsonResponse.getJSONObject("main");
            double temp = main.getDouble("temp");
            int humidity = main.getInt("humidity");

            JSONObject weather = jsonResponse.getJSONArray("weather").getJSONObject(0);
            String description = weather.getString("description");

            // Display Output
            System.out.println("------ Weather Info ------");
            System.out.println("City       : " + cityName);
            System.out.println("Temperature: " + temp + " °C");
            System.out.println("Humidity   : " + humidity + "%");
            System.out.println("Condition  : " + description);
            System.out.println("--------------------------");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
