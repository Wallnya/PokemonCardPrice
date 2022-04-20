package com.pcp.pokemoncardprice.ui.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
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

import com.pcp.pokemoncardprice.R;
import com.pcp.pokemoncardprice.databinding.FragmentGraphBinding;
import com.pcp.pokemoncardprice.jsonreader.JsonReader;
import com.pcp.pokemoncardprice.ui.card_info.CardsInfoViewModel;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class GraphFragment extends Fragment {

    private FragmentGraphBinding binding;
    private CardsInfoViewModel cardsInfoViewModel;
    private JsonReader jsonReader = new JsonReader();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GraphViewModel graphViewModel =
                new ViewModelProvider(this).get(GraphViewModel.class);
        cardsInfoViewModel =
                new ViewModelProvider(requireActivity()).get(CardsInfoViewModel.class);
        binding = FragmentGraphBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<String> arrayDate = new ArrayList<>();

        final TextView textView = binding.textDashboard;
        graphViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        String jsonString = jsonReader.read(getContext(), "data.json");
        JSONObject obj;
        Date date;
        try {
            obj = new JSONObject(jsonString);
            JSONArray array=obj.getJSONArray("data");
            for(int i=0;i<array.length();i++){
                if(array.getJSONObject(i).getString("id").equals(graphViewModel.getID())) {
                    JSONObject userDetail = array.getJSONObject(i);
                    for (int j = 0; j < userDetail.getJSONArray("prices").length(); j++) {
                        String substr_day=array.getJSONObject(i).getJSONArray("prices").getJSONObject(j).getString("date").substring(0,2);
                        String substr_month=array.getJSONObject(i).getJSONArray("prices").getJSONObject(j).getString("date").substring(3,5);
                        String substr_year=array.getJSONObject(i).getJSONArray("prices").getJSONObject(j).getString("date").substring(6,10);

                        String value = userDetail.getJSONArray("prices").getJSONObject(j).getString("prix");
                        values.add(new Entry(j,Float.parseFloat(value)));
                        arrayDate.add(substr_day+"-"+ substr_month+"-"+substr_year);
                    }
                    String lastDate =array.getJSONObject(i).getJSONArray("prices").getJSONObject(userDetail.getJSONArray("prices").length()-1).getString("date");
                    binding.textDashboard.setText("Carte moyenne du"+ lastDate +" :\n"+userDetail.getJSONArray("prices").getJSONObject(userDetail.getJSONArray("prices").length()-1).getString("prix")+"€");
                }
            }

            binding.chart.setTouchEnabled(true);
            binding.chart.setPinchZoom(true);

            CustomMarkerView mv = new CustomMarkerView(getContext(), R.layout.custom_marker_view);
            // set the marker to the chart
            binding.chart.setMarkerView(mv);
            //Design x and y axis.
            XAxis xAxis = binding.chart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(getDate(arrayDate)));
            if(values.size() > 6 )
                xAxis.setLabelCount(6, true);
            else
                xAxis.setLabelCount(values.size(), true);


            binding.chart.setExtraOffsets(10, 0, 25, 0);
            YAxis rightAxis = binding.chart.getAxisRight();
            rightAxis.setEnabled(false);
            LineDataSet set1;
            if (binding.chart.getData() != null &&
                    binding.chart.getData().getDataSetCount() > 0) {
                set1 = (LineDataSet) binding.chart.getData().getDataSetByIndex(0);
                set1.setValues(values);
                binding.chart.getData().notifyDataChanged();
                binding.chart.notifyDataSetChanged();
            } else {
                set1 = new LineDataSet(values, "Prix");
                binding.chart.getLegend().setEnabled(false); // ne pas afficher la légence
                binding.chart.getDescription().setEnabled(false); // enlever le description label en bas à droite
                set1.setDrawIcons(false);
                set1.enableDashedLine(10f, 5f, 0f);
                set1.enableDashedHighlightLine(10f, 5f, 0f);
                set1.setColor(Color.DKGRAY);
                set1.setCircleColor(Color.DKGRAY);
                set1.setLineWidth(1f);
                set1.setCircleRadius(3f);
                set1.setDrawCircleHole(false);
                set1.setValueTextSize(9f);
                set1.setDrawFilled(true);
                set1.setFormLineWidth(1f);
                set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                set1.setFormSize(15.f);
                set1.setFillColor(Color.rgb(126, 185, 193));
                set1.setValueFormatter(new DecimalPoint());
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);
                LineData data = new LineData(dataSets);
                binding.chart.setData(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        binding.button.setOnClickListener(v -> {
            String playerTag = graphViewModel.getID();
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

    private ArrayList<String> getDate(ArrayList date2) {
        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < date2.size(); i++)
            label.add(date2.get(i).toString());
        return label;
    }

    public class CustomMarkerView extends MarkerView {


        private TextView tvContent;
        private int uiScreenWidth;

        public CustomMarkerView (Context context, int layoutResource) {
            super(context, layoutResource);
            tvContent = (TextView) findViewById(R.id.tvContent);
            uiScreenWidth = getResources().getDisplayMetrics().widthPixels;

        }
        // callbacks everytime the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            tvContent.setText(""+e.getY()); // set the entry-value as the display text
            tvContent.setTextColor(Color.BLACK);
        }
        @Override
        public void draw(Canvas canvas, float posX, float posY) {
            // Check marker position and update offsets.
            int w = getWidth();
            if((uiScreenWidth-posX-w) < w) {
                posX -= w;
            }
            if(posY>900){
                posY -=w;
            }
            // translate to the correct position and draw
            canvas.translate(posX, posY);
            draw(canvas);
            canvas.translate(-posX, -posY);
        }
    }

    public class DecimalPoint extends ValueFormatter {
        private DecimalFormat mFormat;
        public DecimalPoint() {
            mFormat = new DecimalFormat("#.##");
        }
        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value);
        }
    }
}