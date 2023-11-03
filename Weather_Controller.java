    package com.example.weatherapp;

    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.fxml.FXML;
    import javafx.fxml.Initializable;
    import javafx.scene.control.Alert;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;
    import javafx.util.Duration;
    import org.apache.http.client.methods.CloseableHttpResponse;
    import org.apache.http.client.methods.HttpGet;
    import org.apache.http.impl.client.CloseableHttpClient;
    import org.apache.http.impl.client.HttpClients;
    import org.apache.http.util.EntityUtils;
    import org.json.JSONObject;

    import java.net.URL;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.ResourceBundle;

    public class Weather_Controller implements Initializable {
        @FXML private TextField location;
        @FXML private Button search;
        @FXML private Label time;
        @FXML private Label city;
        @FXML private Label weather;
        @FXML private Label temp;
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            updateDateTime();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateDateTime()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            getWeather();
        }

        private void updateDateTime() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");
            String dateTime = dateFormat.format(new Date());
            time.setText(dateTime);
        }

        private void getWeather(){
            search.setOnAction(event -> {
                String city1 = location.getText();
                String apiKey = "fe4dca23a353d7b5d03f613add36adcc";
                try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                    String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city1 + "&appid=" + apiKey;
                    HttpGet httpGet = new HttpGet(apiUrl);

                    try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                        String responseBody = EntityUtils.toString(response.getEntity());

                        JSONObject data = new JSONObject(responseBody);
                        if (data.has("weather")) {
                            String weatherDescription = data.getJSONArray("weather").getJSONObject(0).getString("description");
                            double temperature = data.getJSONObject("main").getDouble("temp");
                            float temp1 = (float) (temperature-273);
                            String temp2 = String.valueOf(temp1);
                            String tem = String.valueOf(temperature);

                           city.setText(city1);
                           weather.setText(weatherDescription);
                           temp.setText(temp2+" °C or "+tem+" K");
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Not found!");
                            alert.setContentText("Weather data not found for the specified city.");
                            alert.showAndWait();

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }

    }
