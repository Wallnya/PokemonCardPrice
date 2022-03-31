package com.example.pokemoncardprice.ui.favoris;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemoncardprice.R;
import com.example.pokemoncardprice.models.CardItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavorisListItemAdapter extends RecyclerView.Adapter<FavorisListItemAdapter.ViewHolder>{
    private List<CardItem> mDataSet;
    private FavorisListItemAdapter.ItemClickListener mClickListener;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView releasedDateView;
        private final TextView nameView;
        private final TextView extensionView;
        private final ImageView extensionImageView;

        public ViewHolder(View v) {
            super(v);
            releasedDateView = (TextView) v.findViewById(R.id.textView6);
            nameView = (TextView) v.findViewById(R.id.textView4);
            extensionView = (TextView) v.findViewById(R.id.textView5);
            extensionImageView = (ImageView) v.findViewById(R.id.imageView);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        public TextView getReleasedDateView() {
            return releasedDateView;
        }

        public TextView getNameView() {
            return nameView;
        }

        public TextView getExtensionView() {return extensionView;}

        public ImageView getExtensionImageView() {return extensionImageView;}
    }

    public FavorisListItemAdapter(List<CardItem> dataSet) {
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public FavorisListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_cards_item, viewGroup, false);

        return new FavorisListItemAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavorisListItemAdapter.ViewHolder viewHolder, final int position) {
        CardItem CardItem = mDataSet.get(position);
        viewHolder.getReleasedDateView().setText(CardItem.getReleasedDate());
        viewHolder.getNameView().setText(CardItem.getName());
        viewHolder.getExtensionView().setText(CardItem.getExtension());
        Picasso.get().load(CardItem.getExtensionImage()).into(viewHolder.getExtensionImageView());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    void setClickListener(FavorisListItemAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
