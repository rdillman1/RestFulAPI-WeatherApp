package edu.twu.rdillman1.myfinalproject_weatherapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {
    public static final String CITY_ID_QUERY ="https://www.metaweather.com/api/location/search/?query=";
    public static final String FORCAST_QUERY ="https://www.metaweather.com/api/location/";
    Context context;
    String cityID="";
//We have to wait for the volley to return something, callback was needed
    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String theCityID);
    }
    public WeatherDataService(Context context){
        this.context =context;
    }

    public void getCityID(String cityName, VolleyResponseListener volleyResponseListener) {
        String url = CITY_ID_QUERY+cityName;

// Request a string response from the provided URL.
        JsonArrayRequest jRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject cityData = response.getJSONObject(0);
                    cityID = cityData.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(cityID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                volleyResponseListener.onError("Something went wrong");
            }
        });
        //queue.add(jRequest);
        WeatherDataSingleton.getInstance(context).addToRequestQueue(jRequest);
    }

    public interface ForcastResponse{
        void onError(String message);

        void onResponse(WeatherModel weatherModel);
    }
  public void getForcast(String theCityID, ForcastResponse forcastResponse){
        String url = FORCAST_QUERY+theCityID;
      List<WeatherModel> report = new ArrayList<>();
      //get JSON object "consolodated_weather

      JsonObjectRequest jRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
              try {
                  JSONArray consoladated_weather_array = response.getJSONArray("consolidated_weather");
                  //get item from array
                  WeatherModel day = new WeatherModel();
                  JSONObject day_from_api = (JSONObject) consoladated_weather_array.get(0);
             day.setId(day_from_api.getInt("id"));
             day.setWeather_state_name(day_from_api.getString("weather_state_name"));
             day.setWeather_state_abbr(day_from_api.getString("weather_state_abbr"));
             day.setWind_direction_compass(day_from_api.getString("wind_direction_compass"));
             day.setCreated(day_from_api.getString("created"));
             day.setApplicable_date(day_from_api.getString("applicable_date"));
             day.setMin_temp(day_from_api.getLong("min_temp"));
             day.setMax_temp(day_from_api.getLong("max_temp"));
             day.setThe_temp(day_from_api.getLong("the_temp"));
             day.setWind_speed(day_from_api.getLong("wind_speed"));
             day.setAir_pressure(day_from_api.getLong("air_pressure"));
             day.setHumidity(day_from_api.getInt("humidity"));
             day.setVisibility(day_from_api.getLong("visibility"));
             day.setPredictability(day_from_api.getInt("predictability"));

             forcastResponse.onResponse(day);
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

          }
      });
              //get each item from JSON array to place forcast.
      WeatherDataSingleton.getInstance(context).addToRequestQueue(jRequest);
  }
}
