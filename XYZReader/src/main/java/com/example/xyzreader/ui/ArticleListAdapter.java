package com.example.xyzreader.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.util.DateUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleListAdapter extends Adapter<ArticleListAdapter.ArticleViewHolder> {

    private final OnItemClickListener mListener;
    private Cursor mCursor;
    private Context mContext;

    @BindString(R.string.subtitle_format)
    String subtitleFormat;

    public ArticleListAdapter(Cursor cursor, Context context, OnItemClickListener listener) {
        mListener = listener;
        mCursor = cursor;
        mContext = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article, parent, false);
        final ArticleViewHolder viewHolder = new ArticleViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onItemClick(getItemId(viewHolder.getAdapterPosition()));
            }
        });

        ButterKnife.bind(this, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.titleView.setText(mCursor.getString(ArticleLoader.Query.TITLE));
        holder.subtitleView.setText(
                Html.fromHtml(String.format(subtitleFormat, DateUtils.getPublishedDate(mCursor), mCursor.getString(ArticleLoader.Query.AUTHOR)))
        );
        holder.thumbnailView.setImageUrl(
                mCursor.getString(ArticleLoader.Query.THUMB_URL),
                ImageLoaderHelper.getInstance(mContext).getImageLoader());
        holder.thumbnailView.setAspectRatio(mCursor.getFloat(ArticleLoader.Query.ASPECT_RATIO));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(ArticleLoader.Query._ID);
    }

    public interface OnItemClickListener {
        void onItemClick(long id);
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)
        DynamicHeightNetworkImageView thumbnailView;

        @BindView(R.id.article_title)
        TextView titleView;

        @BindView(R.id.article_subtitle)
        TextView subtitleView;

        ArticleViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

}
