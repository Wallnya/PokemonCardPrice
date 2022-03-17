package com.example.pokemonmartin.ui.card;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonmartin.models.CardItem;

import java.util.List;
import com.example.pokemonmartin.R;

public class CardsListItemAdapter extends RecyclerView.Adapter<CardsListItemAdapter.ViewHolder> {
    private List<CardItem> mDataSet;
    private CardsListItemAdapter.ItemClickListener mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView rankView;
        private final TextView nameView;
        private final TextView clubNameView;

        public ViewHolder(View v) {
            super(v);
            rankView = (TextView) v.findViewById(R.id.textView6);
            nameView = (TextView) v.findViewById(R.id.textView4);
            clubNameView = (TextView) v.findViewById(R.id.textView5);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        public TextView getRankView() {
            return rankView;
        }

        public TextView getNameView() {
            return nameView;
        }

        public TextView getClubNameView() {
            return clubNameView;
        }

    }

    public CardsListItemAdapter(List<CardItem> dataSet) {
        mDataSet = dataSet;
    }

    public CardsListItemAdapter(List<CardItem> mDataSet, CardsListItemAdapter.ItemClickListener mClickListener) {
        this.mDataSet = mDataSet;
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public CardsListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_cards_item, viewGroup, false);

        return new CardsListItemAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardsListItemAdapter.ViewHolder viewHolder, final int position) {
        CardItem CardItem = mDataSet.get(position);

        viewHolder.getRankView().setText(CardItem.getId());
        viewHolder.getNameView().setText(CardItem.getName());
        viewHolder.getClubNameView().setText(CardItem.getExtension());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    CardItem getItem(int id) {
        return mDataSet.get(id);
    }

    void setClickListener(CardsListItemAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
