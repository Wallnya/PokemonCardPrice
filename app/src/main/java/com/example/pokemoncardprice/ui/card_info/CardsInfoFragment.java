package com.example.pokemoncardprice.ui.card_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pokemoncardprice.databinding.FragmentCardinfoBinding;
import com.squareup.picasso.Picasso;


public class CardsInfoFragment extends Fragment {

    private CardsInfoViewModel cardsInfoViewModel;
    private FragmentCardinfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardsInfoViewModel =
                new ViewModelProvider(requireActivity()).get(CardsInfoViewModel.class);

        binding = FragmentCardinfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //System.out.println("coucou"+cardsInfoViewModel.getCardInfo(""));
        cardsInfoViewModel.getLastPlayer().observe(getViewLifecycleOwner(), cardinfoItem -> {
            if (!cardinfoItem.equals(null)) {
                binding.pokemonname.setText(cardinfoItem.getName() + " - " + cardinfoItem.getRarity());
                binding.cardMarketAverageTextFill.setText(cardinfoItem.getcardMarketaverageSellPrice());
                binding.cardMarketAvg1Fill.setText(cardinfoItem.getcardMarketavg1());
                binding.cardMarketAvg2Fill.setText(cardinfoItem.getcardMarketavg7());
                binding.cardMarketAvg3Fill.setText(cardinfoItem.getcardMarketavg30());
                binding.tcgMarketMarketTextFill.setText(cardinfoItem.getTcgPlayerMarket());
                binding.tcgMarketLowFill.setText(cardinfoItem.getTcgPlayerLow());
                binding.tcgMarketMidFill.setText(cardinfoItem.getTcgPlayerMid());
                binding.tcgMarketHighFill.setText(cardinfoItem.getTcgPlayerHigh());
                Picasso.get().load(cardinfoItem.getCardImage()).into(binding.playerAvatar);
                binding.Stats.setText("Stats (" + cardinfoItem.getDate() + ")");
            }

        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}