package com.example.pokemoncardprice.net.core;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class PokemonRequestQueue {
    private static PokemonRequestQueue instance;
    private RequestQueue requestQueue;
    private static Context ctx;
    public static final String API_TOKEN = "39c7066c-5a49-4e8f-83e5-eef5b11535af";

    private PokemonRequestQueue(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }
    public static synchronized PokemonRequestQueue getInstance(Context context) {
        if (instance == null) {
            instance = new PokemonRequestQueue(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
