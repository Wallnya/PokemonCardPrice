package com.pcp.pokemoncardprice.ui.extension;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pcp.pokemoncardprice.R;
import com.pcp.pokemoncardprice.databinding.FragmentExtensionBinding;
import com.pcp.pokemoncardprice.models.ExtensionItem;
import com.pcp.pokemoncardprice.models.VerticalSpacingDecoration;
import com.pcp.pokemoncardprice.ui.card.CardsViewModel;

import java.util.Collections;
import java.util.stream.StreamSupport;

public class ExtensionFragment extends Fragment {

    private ExtensionViewModel extensionViewModel;
    private CardsViewModel cardsViewModel;
    private FragmentExtensionBinding binding;
    private static final int VERTICAL_ITEM_SPACE = 48;

    @SuppressLint("NewApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        extensionViewModel =
                new ViewModelProvider(this).get(ExtensionViewModel.class);

        cardsViewModel =
                new ViewModelProvider(this).get(CardsViewModel.class);

        binding = FragmentExtensionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.extensionListView;

        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        recyclerView.addItemDecoration(new VerticalSpacingDecoration(VERTICAL_ITEM_SPACE));


        extensionViewModel.getExtension().observe(getViewLifecycleOwner(), extensionItems -> {
            Collections.sort(extensionItems, ExtensionItem.byDate);
            ExtensionListItemAdapter adapter = new ExtensionListItemAdapter(extensionItems);

            //On cache le texte et l'animation car tout a été chargé
            binding.uppertext.setVisibility(View.GONE);
            binding.loadinganimation.setVisibility(View.GONE);
            adapter.setClickListener((view, position) -> {
                //System.out.println("coucou");
                ExtensionItem selectedExtensionItem = extensionItems.get(position);
                System.out.println("position :"+position);
                System.out.println("selectedExtensionItem id :"+selectedExtensionItem.getId());

                cardsViewModel.getCardbySet(selectedExtensionItem.getId()).observe(getViewLifecycleOwner(), cardItem -> {
                    System.out.println("coucou les amis, je suis passée");
                    if (cardItem != null) {
                        Navigation.findNavController(root).navigate(R.id.action_extensionFragment_to_cardsBySetFragment);
                    } else {
                        Toast.makeText(getContext(), "Une erreur est parvenue pendant la recherche de l'extension", Toast.LENGTH_LONG).show();
                    }
                });
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