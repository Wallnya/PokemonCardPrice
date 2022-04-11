package com.example.pokemoncardprice.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pokemoncardprice.net.core.PokemonRequestQueue;

import org.json.JSONObject;

public class CardAPI {

    static String API_ENDPOINT = "https://api.pokemontcg.io/v2/cards/?q=name:";
    public static void getCard(String playerTag, Context ctx, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError) {
        String requestUrl = API_ENDPOINT + playerTag;
        JsonObjectRequest cardRequest = new JsonObjectRequest
                (Request.Method.GET, requestUrl, null, onResponse, null) {
        };
        PokemonRequestQueue.getInstance(ctx).addToRequestQueue(cardRequest);
    }
}
