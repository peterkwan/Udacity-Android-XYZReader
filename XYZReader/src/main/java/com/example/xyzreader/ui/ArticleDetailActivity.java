package com.example.xyzreader.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;

/**
 * An activity representing a single Article detail screen, letting you swipe between articles.
 */
public class ArticleDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private long mStartItemId;
    private long mSelectedItemId;
    private Cursor mCursor;
    private ArticleDetailViewPagerAdapter mViewPagerAdapter;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        ButterKnife.bind(this);

        getSupportLoaderManager().initLoader(0, null, this);

        mViewPagerAdapter = new ArticleDetailViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mViewPagerAdapter);

        if (savedInstanceState == null) {
            if (getIntent() != null && getIntent().getData() != null) {
                mStartItemId = ItemsContract.Items.getItemId(getIntent().getData());
                mSelectedItemId = mStartItemId;
            }
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return ArticleLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursor = null;
        mViewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mCursor = cursor;
        mViewPagerAdapter.notifyDataSetChanged();

        if (mStartItemId > 0) {
            mCursor.moveToFirst();
            do {
                if (mCursor.getLong(ArticleLoader.Query._ID) == mStartItemId) {
                    int position = mCursor.getPosition();
                    viewPager.setCurrentItem(position, false);
                    break;
                }
            } while (mCursor.moveToNext());

            mStartItemId = 0;
        }
    }

    @OnClick(R.id.action_up)
    void onUpNavigationClicked() {
        onSupportNavigateUp();
    }

    @OnPageChange(value = R.id.pager, callback = OnPageChange.Callback.PAGE_SELECTED)
    void onViewPageSelected(int position) {
        if (mCursor != null)
            mCursor.moveToPosition(position);
        mSelectedItemId = mCursor.getLong(ArticleLoader.Query._ID);
    }

    class ArticleDetailViewPagerAdapter extends FragmentStatePagerAdapter {

        private static final String ARG_ITEM_ID = "item_id";

        ArticleDetailViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            mCursor.moveToPosition(position);

            Bundle arguments = new Bundle();
            arguments.putLong(ARG_ITEM_ID, mCursor.getLong(ArticleLoader.Query._ID));

            ArticleDetailFragment fragment = new ArticleDetailFragment();
            fragment.setArguments(arguments);

            return fragment;
        }

        @Override
        public int getCount() {
            return mCursor != null ? mCursor.getCount() : 0;
        }
    }
}
