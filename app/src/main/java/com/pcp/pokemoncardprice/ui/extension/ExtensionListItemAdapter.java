package com.pcp.pokemoncardprice.ui.extension;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pcp.pokemoncardprice.R;
import com.pcp.pokemoncardprice.models.CardItem;
import com.pcp.pokemoncardprice.models.ExtensionItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ExtensionListItemAdapter extends RecyclerView.Adapter<ExtensionListItemAdapter.ViewHolder> {
    private List<ExtensionItem> mDataSet;
    private ExtensionListItemAdapter.ItemClickListener mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nameView;
        private final TextView textDate;
        private final ImageView extensionAvatarView;

        public ViewHolder(View v) {
            super(v);
            nameView = (TextView) v.findViewById(R.id.textExtension);
            textDate = (TextView) v.findViewById(R.id.textDate);
            extensionAvatarView = (ImageView) v.findViewById(R.id.imageExtension);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        public TextView getNameView() {
            return nameView;
        }

        public TextView getTextDate() {
            return textDate;
        }

        public ImageView getExtensionImageView() {return extensionAvatarView;}
    }

    public ExtensionListItemAdapter(List<ExtensionItem> dataSet) {
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ExtensionListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.extension_item, viewGroup, false);

        return new ExtensionListItemAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ExtensionItem extensionItem = mDataSet.get(position);
        viewHolder.getNameView().setText(extensionItem.getName());
        viewHolder.getTextDate().setText(extensionItem.getReleaseDate());
        Picasso.get().load(extensionItem.getImage()).into(viewHolder.getExtensionImageView());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    void setClickListener(ExtensionListItemAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
