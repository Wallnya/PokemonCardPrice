package com.example.pokemoncardprice.ui.card_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.pokemoncardprice.R;
import com.example.pokemoncardprice.databinding.FragmentCardinfoBinding;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
public class CardsInfoFragment extends Fragment {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private CardsInfoViewModel cardsInfoViewModel;
    private FragmentCardinfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardsInfoViewModel =
                new ViewModelProvider(requireActivity()).get(CardsInfoViewModel.class);

        binding = FragmentCardinfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cardsInfoViewModel.getLastPlayer().observe(getViewLifecycleOwner(), cardinfoItem -> {
            if (!cardinfoItem.equals(null)) {
                binding.pokemonname.setText(cardinfoItem.getName() + " - " + cardinfoItem.getRarity());
                String str = verification(cardinfoItem.getcardMarketaverageSellPrice());
                binding.cardMarketAverageTextFill.setText(str);
                str = verification(cardinfoItem.getcardMarketavg1());
                binding.cardMarketAvg1Fill.setText(str);
                str = verification(cardinfoItem.getcardMarketavg7());
                binding.cardMarketAvg2Fill.setText(str);
                str = verification(cardinfoItem.getcardMarketavg30());
                binding.cardMarketAvg3Fill.setText(str);
                str = verification(cardinfoItem.getTcgPlayerMarket());
                binding.tcgMarketMarketTextFill.setText(str);
                str = verification(cardinfoItem.getTcgPlayerLow());
                binding.tcgMarketLowFill.setText(str);
                str = verification(cardinfoItem.getTcgPlayerMid());
                binding.tcgMarketMidFill.setText(str);
                str = verification(cardinfoItem.getTcgPlayerHigh());
                binding.tcgMarketHighFill.setText(str);
                Picasso.get().load(cardinfoItem.getCardImage()).into(binding.cardImage);
                binding.Stats.setText("Stats (" + cardinfoItem.getDate() + ")");
            }
        });
        binding.cardImage.setOnClickListener(v -> {
            Navigation.findNavController(root).navigate(R.id.navigation_dashboard);
        });
        return root;
    }

    public String verification(String valeur){
        String str ="null";
        if(!valeur.equals(" / ")){
            str = ""+df.format(Float.valueOf(valeur)/1.1f)+"€";
        }
        return str;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}