package com.example.weather.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.BaseApp;
import com.example.weather.models.WeatherInfo;
import com.example.weather.networking.Service;


import javax.inject.Inject;

public class HomeActivity extends BaseApp implements HomeView {

    @Inject
    public Service service;
    ProgressBar progressBar;
    TextView cityText;
    TextView conditionText;
    TextView tempText;
    TextView humidityText;
    TextView windText;
    EditText city;
    Button submit_button;
    Button refresh_button;
    RelativeLayout myView;
    HomePresenter presenter;
    public static final String PREFS_NAME = "MyApp_Settings";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    public static final String DEFAULT = "Inglewood";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);

        renderView();
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();

      presenter = new HomePresenter(service, this);
      if(settings.getString("key","").isEmpty()) {
          presenter.getWeather(DEFAULT);
      }
      else{
          presenter.getWeather(settings.getString("key",""));
      }
    }

    public  void renderView(){
        setContentView(R.layout.activity_home);
        progressBar = findViewById(R.id.progress);
        cityText  =findViewById(R.id.city_text);
        conditionText = findViewById(R.id.weather_condition);
        tempText = findViewById(R.id.temp_text);
        humidityText = findViewById(R.id.humidity_text);
        windText = findViewById(R.id.wind_text);
        city = findViewById(R.id.edit_text);
        submit_button = findViewById(R.id.submit);
        refresh_button = findViewById(R.id.refresh_button);
        myView = findViewById(R.id.main_view);

        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.setVisibility(View.GONE);
                city.setVisibility(View.VISIBLE);
                submit_button.setVisibility(View.VISIBLE);
                refresh_button.setVisibility(View.GONE);
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.setVisibility(View.VISIBLE);
                city.setVisibility(View.GONE);
                submit_button.setVisibility(View.GONE);
                refresh_button.setVisibility(View.VISIBLE);
                presenter.getWeather(city.getText().toString());
                editor.putString("key",city.getText().toString());
                editor.commit();
            }
        });

    }


    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getWeatherInfoSuccess(WeatherInfo weatherInfo) {
            String city = weatherInfo.getName();
            String condition = weatherInfo.getWeather().get(0).getDescription();
            String temp = weatherInfo.getMain().getTemp() +
                    getString(R.string.celsius);
            String humidity = getString(R.string.humidity) + ": " +
                    weatherInfo.getMain().getHumidity() + "%";
            String wind = getString(R.string.wind_speed) + ": " +
                    weatherInfo.getWind().getSpeed() + " m/s";

            cityText.setText(city);
            conditionText.setText(condition);
            tempText.setText(temp);
            humidityText.setText(humidity);
            windText.setText(wind);
    }
}
