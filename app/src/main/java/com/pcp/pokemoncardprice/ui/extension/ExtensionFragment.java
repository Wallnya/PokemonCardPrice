package com.pcp.pokemoncardprice.ui.extension;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcp.pokemoncardprice.R;
import com.pcp.pokemoncardprice.databinding.FragmentExtensionBinding;
import com.pcp.pokemoncardprice.models.CardItem;
import com.pcp.pokemoncardprice.models.ExtensionItem;
import com.pcp.pokemoncardprice.models.VerticalSpacingDecoration;

import java.util.Collections;

public class ExtensionFragment extends Fragment {

    private ExtensionViewModel extensionViewModel;
    private FragmentExtensionBinding binding;
    private static final int VERTICAL_ITEM_SPACE = 48;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        extensionViewModel =
                new ViewModelProvider(this).get(ExtensionViewModel.class);

        binding = FragmentExtensionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.extensionListView;

        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        recyclerView.addItemDecoration(new VerticalSpacingDecoration(VERTICAL_ITEM_SPACE));


        extensionViewModel.getExtension().observe(getViewLifecycleOwner(), brawlerItems -> {
            Collections.sort(brawlerItems, ExtensionItem.byDate);
            ExtensionListItemAdapter adapter = new ExtensionListItemAdapter(brawlerItems);

            //On cache le texte et l'animation car tout a été chargé
            binding.uppertext.setVisibility(View.GONE);
            binding.loadinganimation.setVisibility(View.GONE);

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