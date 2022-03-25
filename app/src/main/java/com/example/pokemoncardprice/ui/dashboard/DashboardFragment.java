package com.example.pokemoncardprice.ui.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.pokemoncardprice.R;
import com.example.pokemoncardprice.databinding.FragmentDashboardBinding;
import com.example.pokemoncardprice.ui.card.CardsViewModel;
import com.example.pokemoncardprice.ui.card_info.CardsInfoViewModel;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private CardsInfoViewModel cardsInfoViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        cardsInfoViewModel =
                new ViewModelProvider(requireActivity()).get(CardsInfoViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        String jsonString = read(getContext(), "data.json");
        JSONObject obj;
        Date date;
        try {
            obj = new JSONObject(jsonString);
            JSONArray array=obj.getJSONArray("data");
            DataPoint[] dp = null;
            for(int i=0;i<array.length();i++){
                if(array.getJSONObject(i).getString("id").equals(dashboardViewModel.getID())) {
                    JSONObject userDetail = array.getJSONObject(i);
                    dp = new DataPoint[userDetail.getJSONObject("prices").length()];
                    for (int j = 0; j < userDetail.getJSONObject("prices").length(); j++) {
                        date = new SimpleDateFormat("yyyy/MM/dd").parse(array.getJSONObject(i).getJSONObject("prices").getString("date"));
                        dp[j] = new DataPoint(date,
                                Double.parseDouble(userDetail.getJSONObject("prices").getString("prix")));
                    }
                    binding.textDashboard.setText("Carte moyenne du dernier jour : "+userDetail.getJSONObject("prices").getString("prix")+"€");
                }
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
            binding.idGraphView.addSeries(series);

            DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(getContext());
            binding.idGraphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity(), dateFormat));
            binding.idGraphView.getGridLabelRenderer().setTextSize(32);
            binding.idGraphView.getGridLabelRenderer().setNumHorizontalLabels(4);
            binding.idGraphView.getViewport().setMinY(0.0);
            binding.idGraphView.getViewport().setScrollable(true);
            binding.idGraphView.getViewport().setScrollableY(true);
            series.setColor(Color.RED);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(6);
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }

        binding.button.setOnClickListener(v -> {
            String playerTag = dashboardViewModel.getID();
            cardsInfoViewModel.getCardInfo(playerTag).observe(getViewLifecycleOwner(), cardItem -> {
                if (!cardItem.equals(null)) {
                        Navigation.findNavController(root).navigate(R.id.action_navigation_dashboard_to_navigation_notifications);
                }
                else {
                    Toast.makeText(getContext(), "Pas de carte", Toast.LENGTH_LONG).show();
                }
            });
        });
        return root;
    }

    private String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}