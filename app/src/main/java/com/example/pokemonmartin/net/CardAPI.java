package com.example.pokemonmartin.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pokemonmartin.net.core.PokemonRequestQueue;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class CardAPI {

    static String API_ENDPOINT = "https://api.pokemontcg.io/v2/cards/?q=name:";
    public static void getCard(String playerTag, Context ctx, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError) {
        String requestUrl = API_ENDPOINT + playerTag;
        JsonObjectRequest cardRequest = new JsonObjectRequest
                (Request.Method.GET, requestUrl, null, onResponse, null) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("authorization", "Bearer " + PokemonRequestQueue.API_TOKEN);
                return params;
            }
        };
        System.out.println("url :"+requestUrl);
        PokemonRequestQueue.getInstance(ctx).addToRequestQueue(cardRequest);
    }
}
