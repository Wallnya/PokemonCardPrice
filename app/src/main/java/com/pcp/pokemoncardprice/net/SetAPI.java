package com.pcp.pokemoncardprice.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pcp.pokemoncardprice.net.core.PokemonRequestQueue;

import org.json.JSONObject;

public class SetAPI {
    static String API_ENDPOINT = "https://api.pokemontcg.io/v2/sets";
    public static void getExtension(Context ctx, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError) {
        JsonObjectRequest cardRequest = new JsonObjectRequest
                (Request.Method.GET, API_ENDPOINT, null, onResponse, null) {
        };
        PokemonRequestQueue.getInstance(ctx).addToRequestQueue(cardRequest);
    }
}
