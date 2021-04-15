package edu.twu.rdillman1.myfinalproject_weatherapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class WeatherDataSingleton {
        private static WeatherDataSingleton instance;
        private RequestQueue requestQueue;
       // pulled from android dev idk what it does
       // private ImageLoader imageLoader;
        private static Context ctx;

        private WeatherDataSingleton(Context context) {
            ctx = context;
            requestQueue = getRequestQueue();
        }

        public static synchronized WeatherDataSingleton getInstance(Context context) {
            if (instance == null) {
                instance = new WeatherDataSingleton(context);
            }
            return instance;
        }
        public RequestQueue getRequestQueue() {
            if (requestQueue == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
            }
            return requestQueue;
        }
        public <T> void addToRequestQueue(Request<T> req) {
            getRequestQueue().add(req);
        }
    }
