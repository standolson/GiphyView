package com.niketest.giphyview.net;

import android.os.AsyncTask;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Extension of {@link AsyncTask} for sending a {@link GiphyNetRequest} and receiving back a
 * {@link GiphyNetResponse}.
 *
 * @param <T> A {@link GiphyNetRequest} to be sent
 */
public class GiphyNetTask<T extends GiphyNetRequest>
{
	private T request;
	private Callback callback;
	private NetRequest netRequest;
	private Exception exception;

	// A shared OkHttpClient object used by all tasks.  Helps with proper caching of
	// result.
	private static OkHttpClient client;

	/**
	 * An interface for returning a {@GiphyNetResponse} or an {@link Exception} to the
	 * owner of this task.
 	 */
	public interface Callback
	{
		void onSuccess(GiphyNetResponse response);
		void onFailure(Exception e);
	}

	public GiphyNetTask(T request, Callback callback)
	{
		this.request = request;
		this.callback = callback;

		if (client == null)
			client = new OkHttpClient();
	}

	/**
	 * Execute the {@GiphyNetTask}
 	 */
	public void execute()
	{
		netRequest = new NetRequest();
		netRequest.execute();
	}

	/**
	 * Cancel the {@link GiphyNetTask}
 	 */
	public void cancel()
	{
		if (netRequest != null)
			netRequest.cancel(true);
	}

	/**
	 * An {@link AsyncTask} that executes the {@link GiphyNetRequest} and returns via
	 * the {@link Callback} either the {@link GiphyNetResponse} or an error
 	 */
	private class NetRequest extends AsyncTask<Void, Void, GiphyNetResponse>
	{
		Response okResponse;
		Call okCall;

		@Override
		public void onPreExecute()
		{
		}

		@Override
		public GiphyNetResponse doInBackground(Void... params)
		{
			try
			{
				// Create the request, send it and get the response
				Request okRequest = new Request.Builder().url(request.getURL()).build();
				okCall = client.newCall(okRequest);
				okResponse = okCall.execute();

				// Success?
				if (!okResponse.isSuccessful())
					return null;

				// Parse the response with GSON and return it
				Gson gson = new Gson();
				GiphyNetResponse giphyResponse = gson.fromJson(okResponse.body().charStream(), GiphyNetResponse.class);
				return giphyResponse;
			}
			catch (Exception e)
			{
				exception = e;
				return null;
			}
			finally
			{
				// Shutdown the response to avoid leaking
				if (okResponse != null)
					okResponse.close();
			}
		}

		@Override
		public void onCancelled()
		{
			if (okCall != null)
				okCall.cancel();
		}

		@Override
		public void onPostExecute(GiphyNetResponse response)
		{
			if (callback != null)
			{
				if (response != null)
					callback.onSuccess(response);
				else
					callback.onFailure(exception);
			}
		}
	}
}
