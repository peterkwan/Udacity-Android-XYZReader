package com.example.xyzreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ItemsContract;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * An activity representing a list of Articles. This activity has different presentations for
 * handset and tablet-size devices. On handsets, the activity presents a list of items, which when
 * touched, lead to a {@link ArticleDetailActivity} representing item details. On tablets, the
 * activity presents a grid of items as cards.
 */
public class ArticleListActivity extends AppCompatActivity implements ArticleListFragment.OnListItemClickListener {

    private static final String ARG_ITEM_ID = "item_id";

    @BindView(R.id.detailFragmentContainer)
    View detailFragmentContainer;

    @BindBool(R.bool.is_two_pane_layout)
    boolean isTwoPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        ButterKnife.bind(this);

        detailFragmentContainer.setVisibility(isTwoPaneLayout ? VISIBLE : GONE);
    }

    @Override
    public void onListItemClicked(long id) {
        if (isTwoPaneLayout) {
            Bundle arguments = new Bundle();
            arguments.putLong(ARG_ITEM_ID, id);
            ArticleDetailFragment fragment = new ArticleDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailFragmentContainer, fragment)
                    .commit();
        }
        else
            startActivity(new Intent(Intent.ACTION_VIEW, ItemsContract.Items.buildItemUri(id)));
    }
}
