package com.niketest.giphyview.ui;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.niketest.giphyview.R;

public class GiphyMainActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.giphy_main_activity);

		// Setup the toolbar
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		// Find the fragment or create and add it
		GiphyTrendingFragment fragment =
			(GiphyTrendingFragment)getFragmentManager().findFragmentByTag(GiphyTrendingFragment.class.getSimpleName());
		if (fragment == null)
		{
			fragment = new GiphyTrendingFragment();
			if (fragment != null)
			{
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.add(R.id.giphy_fragment_container, fragment, fragment.getClass().getSimpleName());
				ft.commit();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.giphy_main_menu, menu);

		// Setup the SearchView so that it will be displayed when the
		// user clicks the search icon on the action bar.
		final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
		SearchManager searchManager =
			(SearchManager)getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView =
			(SearchView)menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setQueryHint(getString(R.string.search_hint));
		searchView.setIconified(true);

		// We need to setup a query listener so that when the user submits the query
		// the SearchView will be collapsed.  We don't want the user to see an open
		// SearchView when they return here from their search result.  We also reset
		// the contents of the view to an empty string for the next query.
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
		{
			@Override
			public boolean onQueryTextSubmit(String query)
			{
				searchView.setQuery("", false);
				searchView.setIconified(true);
				searchMenuItem.collapseActionView();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText)
			{
				return false;
			}
		});

		return true;
	}
}
