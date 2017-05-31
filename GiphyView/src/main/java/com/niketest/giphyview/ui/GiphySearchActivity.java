package com.niketest.giphyview.ui;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.niketest.giphyview.R;

public class GiphySearchActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.giphy_main_activity);

		// Setup the toolbar
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);


		// Get the search query string
		String queryString = "";
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction()))
			queryString = intent.getStringExtra(SearchManager.QUERY);

		// Find the fragment or create and add it
		GiphySearchFragment fragment =
			(GiphySearchFragment)getFragmentManager().findFragmentByTag(GiphySearchFragment.class.getSimpleName());
		if (fragment == null)
		{
			fragment = new GiphySearchFragment();
			Bundle args = new Bundle();
			args.putString(GiphySearchFragment.EXTRA_QUERY_STRING, queryString);
			fragment.setArguments(args);
			if (fragment != null)
			{
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.add(R.id.giphy_fragment_container, fragment, fragment.getClass().getSimpleName());
				ft.commit();
			}
		}
	}
}

