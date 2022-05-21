package com.pcp.pokemoncardprice.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pcp.pokemoncardprice.net.core.PokemonRequestQueue;

import org.json.JSONObject;

public class CardBySetAPI {

    static String API_ENDPOINT = "https://api.pokemontcg.io/v2/cards?q=set.id:";
    public static void getCardBySet(String id, Context ctx, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError) {
        String requestUrl = API_ENDPOINT + id;
        JsonObjectRequest cardRequest = new JsonObjectRequest
                (Request.Method.GET, requestUrl, null, onResponse, null) {
        };
        PokemonRequestQueue.getInstance(ctx).addToRequestQueue(cardRequest);
        System.out.println("link :"+requestUrl);
    }
}
