package com.example.pokemoncardprice.ui.favoris;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemoncardprice.R;
import com.example.pokemoncardprice.databinding.FragmentCardsearchBinding;
import com.example.pokemoncardprice.databinding.FragmentFavorisBinding;
import com.example.pokemoncardprice.models.VerticalSpacingDecoration;
import com.example.pokemoncardprice.ui.card.CardsListItemAdapter;
import com.example.pokemoncardprice.ui.card.CardsViewModel;
import com.example.pokemoncardprice.ui.card_info.CardsInfoViewModel;
import com.example.pokemoncardprice.ui.card_search.CardSearchFragment;

public class FavorisFragment extends Fragment {

    private CardsViewModel cardsViewModel;
    private FragmentFavorisBinding binding;

    public static CardSearchFragment newInstance() {
        return new CardSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        cardsViewModel =
                new ViewModelProvider(requireActivity()).get(CardsViewModel.class);

        binding = FragmentFavorisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*binding.button.setOnClickListener(v -> {
            String playerTag = binding.playerTag.getText().toString();
            cardsViewModel.getCard(playerTag).observe(getViewLifecycleOwner(), cardItem -> {
                if (!cardItem.isEmpty()) {
                    Navigation.findNavController(root).navigate(R.id.action_cardSearchFragment_to_cardsFragment);
                }
                else {
                    Toast.makeText(getContext(), "Pas de carte", Toast.LENGTH_LONG).show();
                }
            });
        });*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}