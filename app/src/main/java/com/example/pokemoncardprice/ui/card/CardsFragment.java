package com.example.pokemoncardprice.ui.card;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemoncardprice.databinding.FragmentCardsBinding;
import com.example.pokemoncardprice.models.CardItem;
import com.example.pokemoncardprice.models.VerticalSpacingDecoration;
import com.example.pokemoncardprice.ui.card_info.CardsInfoViewModel;


public class CardsFragment extends Fragment {

    private CardsViewModel cardsListViewModel;
    private CardsInfoViewModel cardsInfoViewModel;
    private FragmentCardsBinding binding;
    private static final int VERTICAL_ITEM_SPACE = 24;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardsListViewModel =
                new ViewModelProvider(this).get(CardsViewModel.class);
        cardsInfoViewModel =
                new ViewModelProvider(requireActivity()).get(CardsInfoViewModel.class);

        binding = FragmentCardsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.rankingListView;

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.addItemDecoration(new VerticalSpacingDecoration(VERTICAL_ITEM_SPACE));

        cardsListViewModel.getCard(CardsViewModel.playerTag).observe(getViewLifecycleOwner(), cardListItems -> {
            CardsListItemAdapter adapter = new CardsListItemAdapter(cardListItems);

            adapter.setClickListener((view, position) -> {
                CardItem selectecCardItem = cardListItems.get(position);

                Log.i("POSITION", String.valueOf(position));
                Log.i("CARD_NAME", selectecCardItem.getName());

                /*cardsInfoViewModel.getCardInfo(selectecCardItem.getId()).observe(getViewLifecycleOwner(), cardItem -> {
                    if (cardItem != null) {
                        Navigation.findNavController(root).navigate(R.id.action_cardsFragment_to_navigation_notifications);
                    } else {
                        Toast.makeText(getContext(), "Une erreur est parvenue pendant la recherche de la carte", Toast.LENGTH_LONG).show();
                    }
                });*/
            });

            recyclerView.setAdapter(adapter);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
