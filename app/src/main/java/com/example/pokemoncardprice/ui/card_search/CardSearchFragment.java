package com.example.pokemoncardprice.ui.card_search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.pokemoncardprice.R;

import com.example.pokemoncardprice.databinding.FragmentCardsearchBinding;
import com.example.pokemoncardprice.net.CardAPI;
import com.example.pokemoncardprice.ui.card.CardsViewModel;


public class CardSearchFragment extends Fragment {
    private CardsViewModel cardsViewModel;
    private CardSearchViewModel cardSearchViewModel;
    private FragmentCardsearchBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        cardsViewModel =
                new ViewModelProvider(requireActivity()).get(CardsViewModel.class);

        cardSearchViewModel =
                new ViewModelProvider(requireActivity()).get(CardSearchViewModel.class);

        binding = FragmentCardsearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.button.setOnClickListener(v -> {
            String playerTag = binding.playerTag.getText().toString();
            char[] char_table = playerTag.toCharArray();
            char_table[0]=Character.toUpperCase(char_table[0]);
            playerTag = new String(char_table);
            cardSearchViewModel.getCardName(playerTag, result -> {
                cardsViewModel.getCard(result).observe(getViewLifecycleOwner(), cardItem -> {
                    if (!cardItem.isEmpty()) {
                        Navigation.findNavController(root).navigate(R.id.action_cardSearchFragment_to_cardsFragment);
                    } else {
                        Toast.makeText(getContext(), "Pas de carte", Toast.LENGTH_LONG).show();
                    }
                });
            });
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}