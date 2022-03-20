package com.example.pokemoncardprice.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pokemoncardprice.R;
import com.example.pokemoncardprice.databinding.FragmentDashboardBinding;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    GraphView graphView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        int nb =0;
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray userArray = obj.getJSONArray("data");
            DataPoint[] dp = null;
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject userDetail = userArray.getJSONObject(i);
                 dp = new DataPoint[userDetail.getJSONArray("prices").length()];
                for(int j=0;j<userDetail.getJSONArray("prices").length();j++){
                    Date date=new SimpleDateFormat("dd/MM/yyyy").parse(userDetail.getJSONArray("prices").getJSONObject(j).getString("date"));
                    dp[j] = new DataPoint(date,
                            Double.parseDouble(userDetail.getJSONArray("prices").getJSONObject(j).getString("prix")));
                    nb = j;
                }
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
            binding.idGraphView.setTitleColor(R.color.purple_200);

            // on below line we are setting
            // our title text size.
            //binding.idGraphView.setTitleTextSize(18);
            binding.idGraphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            binding.idGraphView.getViewport().setMinX(series.getLowestValueX());
            binding.idGraphView.getViewport().setMaxX(series.getHighestValueX());
            binding.idGraphView.getGridLabelRenderer().setNumHorizontalLabels(nb);
            // activate horizontal zooming and scrolling
   /*         binding.idGraphView.getViewport().setScalable(true);

// activate horizontal scrolling
            binding.idGraphView.getViewport().setScrollable(true);
            binding.idGraphView.getViewport().setScalableY(true);

// activate vertical scrolling
            binding.idGraphView.getViewport().setScrollableY(true);*/
            // on below line we are adding
            // data series to our graph view.
            binding.idGraphView.addSeries(series);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return root;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getContext().getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}