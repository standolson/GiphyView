package com.niketest.giphyview.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.niketest.giphyview.R;
import com.niketest.giphyview.model.GiphyPhoto;
import com.niketest.giphyview.model.GiphyPhotos;
import com.niketest.giphyview.net.GiphyNetRequest;
import com.niketest.giphyview.net.GiphyNetResponse;
import com.niketest.giphyview.net.GiphyNetTask;
import com.niketest.giphyview.net.GiphyTrendingRequest;
import com.niketest.giphyview.view.GiphyPhotoAdapter;

/**
 * A {@link Fragment} that can perform Giphy API calls.  Use as a subclass and implement
 * the {@link GiphyApiFragment#getRequest()}
 */
public abstract class GiphyApiFragment extends Fragment
	implements GiphyNetTask.Callback, View.OnClickListener
{
	private static final String EXTRA_GIPHY_PHOTOS = "giphyPhotos";

	private ViewGroup rootView;
	private View noResultsView;
	private View spinner;
	private RecyclerView resultsView;
	private GiphyPhotoAdapter resultsAdapter;
	private GridLayoutManager layoutManager;

	protected GiphyPhotos giphyPhotos = new GiphyPhotos();
	private GiphyNetTask<GiphyTrendingRequest> task;

	public GiphyApiFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		rootView = (ViewGroup)inflater.inflate(R.layout.giphy_search_fragment, container, false);

		// Get handles on all of the main UI components
		noResultsView = rootView.findViewById(R.id.no_results_view);
		resultsView = (RecyclerView)rootView.findViewById(R.id.results_view);
		spinner = rootView.findViewById(R.id.spinner);

		// Get the number of columns to display
		int columns = getActivity().getResources().getInteger(R.integer.columns);

		// Setup the search results view
		layoutManager = new GridLayoutManager(getActivity(), columns);
		resultsView.setLayoutManager(layoutManager);
		resultsAdapter = new GiphyPhotoAdapter(getActivity(), giphyPhotos, this);
		resultsView.setAdapter(resultsAdapter);
		resultsView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
			@Override
			public void onLoadMore(int page, int totalItemsCount, RecyclerView view)
			{
				Snackbar.make(rootView, "Total:" + totalItemsCount, Snackbar.LENGTH_SHORT).show();
				sendRequest();
			}
		});

		// Did we have some previous results?  Recover and redisplay them.
		// NOTE: May not be such a good way to save results as if we have
		// too many we may overflow the maximum size of a Binder transaction.
		if (savedInstanceState != null)
		{
			Gson gson = new Gson();
			String giphyPhotosJson = savedInstanceState.getString(EXTRA_GIPHY_PHOTOS);
			GiphyPhotos photos = gson.fromJson(giphyPhotosJson, GiphyPhotos.class);
			giphyPhotos.addAll(photos);
			resultsAdapter.notifyDataSetChanged();
			return rootView;
		}

		// Show the spinner while we wait for results
		showSpinner();

		// Send the request
		sendRequest();

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);

		Gson gson = new Gson();
		savedInstanceState.putString(EXTRA_GIPHY_PHOTOS, gson.toJson(giphyPhotos));
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if (task != null)
			task.cancel();
	}

	@Override
	public void onClick(View v)
	{
		Object tag = v.getTag(R.id.giphy_photo_tag);
		if (tag instanceof GiphyPhoto)
		{
			GiphyPhoto photo = (GiphyPhoto)tag;
			Gson gson = new Gson();
			String photoJson = gson.toJson(photo);

			Intent intent = new Intent(getActivity(), GiphyPhotoViewActivity.class);
			intent.putExtra(GiphyPhotoViewActivity.EXTRA_GIPHY_PHOTO, photoJson);
			startActivity(intent);
		}
	}

	protected abstract GiphyNetRequest getRequest();

	private void sendRequest()
	{
		GiphyNetRequest request = getRequest();
		task = new GiphyNetTask(request, this);
		task.execute();
	}

	private void showSpinner()
	{
		noResultsView.setVisibility(View.GONE);
		resultsView.setVisibility(View.GONE);
		spinner.setVisibility(View.VISIBLE);
	}

	@Override
	public void onSuccess(GiphyNetResponse response)
	{
		// Done waiting and loading
		spinner.setVisibility(View.GONE);

		// No response or data?  Show the "No Results" view!
		if ((response == null) || (response.data == null) || (response.data.size() == 0))
		{
			resultsView.setVisibility(View.GONE);
			noResultsView.setVisibility(View.VISIBLE);
			return;
		}

		// We have results so show them
		resultsView.setVisibility(View.VISIBLE);
		noResultsView.setVisibility(View.GONE);

		// Add the results to the current data set and let the adapter know it has some
		// new stuff to show
		GiphyPhotos newData = response.data;
		giphyPhotos.addAll(newData);
		resultsAdapter.notifyDataSetChanged();
	}

	@Override
	public void onFailure(Exception e)
	{
		// Done waiting and loading
		spinner.setVisibility(View.GONE);

		// Show the error in a SnackBar and allow the user to retry the
		// operation
		String errorStr = getString((e == null) ? R.string.api_failure : R.string.network_failure);
		Snackbar.make(rootView, errorStr, Snackbar.LENGTH_INDEFINITE)
			.setAction(R.string.retry_action, new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					sendRequest();
				}
			})
			.show();
	}
}
