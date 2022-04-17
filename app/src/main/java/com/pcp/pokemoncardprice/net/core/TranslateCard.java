package com.pcp.pokemoncardprice.net.core;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class TranslateCard {
    static String API_ENDPOINT = "https://www.pokepedia.fr/";
    public static void getTranslate(String playerTag, Context ctx, Response.Listener<String> onResponse, Response.ErrorListener onError) {
        String requestUrl = API_ENDPOINT + playerTag;
        StringRequest getRequest = new StringRequest(Request.Method.GET, requestUrl, onResponse, onError);
        PokemonRequestQueue.getInstance(ctx).addToRequestQueue(getRequest);
    }
}
