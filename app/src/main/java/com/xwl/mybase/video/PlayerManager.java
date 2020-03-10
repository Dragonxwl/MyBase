package com.xwl.mybase.video;

import android.net.Uri;
import android.text.TextUtils;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.xwl.mybase.Base.Application;

public class PlayerManager implements Player.EventListener {

	public interface PlayerListener {
		void onPlayerStateChanged(boolean playWhenReady, int playbackState);

		void onLoadingChanged(boolean isLoading);

		void onPlayerError(ExoPlaybackException error);
	}


	private static PlayerManager INSTANCE;

	private SimpleExoPlayer player;
	private DefaultTrackSelector trackSelector;
	private MediaSource mediaSource;

	private String url;
	private PlayerListener mListener;

	private boolean isPlaying = false;

	public synchronized static PlayerManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PlayerManager();
		}
		return INSTANCE;
	}

	public void setListener(PlayerListener mListener) {
		this.mListener = mListener;
	}

	public SimpleExoPlayer getPlayer() {
		return player;
	}

	public void init(String url, boolean playWhenReady) {
		this.url = url;
		if (!TextUtils.isEmpty(this.url)) {
			if (player == null) {
				DefaultTrackSelector.Parameters trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
				TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory();
				trackSelector = new DefaultTrackSelector(trackSelectionFactory);
				trackSelector.setParameters(trackSelectorParameters);

				player = ExoPlayerFactory.newSimpleInstance(Application.getContext(), trackSelector);
				player.setPlayWhenReady(playWhenReady);
				player.addListener(this);
			}
			mediaSource = buildMediaSource(Uri.parse(this.url));
			if (mediaSource != null) {
				player.prepare(mediaSource, false, false);
			}
		}
	}

	public void initRaw(int rawId, boolean playWhenReady) {
		if (player == null) {
			DefaultTrackSelector.Parameters trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
			TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory();
			trackSelector = new DefaultTrackSelector(trackSelectionFactory);
			trackSelector.setParameters(trackSelectorParameters);

			player = ExoPlayerFactory.newSimpleInstance(Application.getContext(), trackSelector);
			player.setPlayWhenReady(playWhenReady);
			player.addListener(this);
		}
		if (rawId != 0) {
			DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(rawId));
			RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(Application.getContext());
			try {
				rawResourceDataSource.open(dataSpec);
				DataSource.Factory factory = () -> rawResourceDataSource;
				mediaSource = new ExtractorMediaSource(rawResourceDataSource.getUri(),
						factory, new DefaultExtractorsFactory(), null, null);
				if (mediaSource != null) {
					player.prepare(mediaSource, false, false);
				}
			} catch (RawResourceDataSource.RawResourceDataSourceException e) {
				e.printStackTrace();
			}
		}

	}

	private MediaSource buildMediaSource(Uri uri) {
		return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("com.pandaabc.stu"))
				.createMediaSource(uri);
	}

	public void start() {
		if (player != null && mediaSource != null) {
			if (player.getPlaybackState() == Player.STATE_IDLE) {
				player.prepare(mediaSource, false, false);
			}
			player.setPlayWhenReady(true);
		}
	}

	public void pause() {
		if (player != null) {
			player.setPlayWhenReady(false);
		}
	}

	public void stop() {
		if (player != null) {
			player.stop();
		}
	}

	public void replay() {
		if (player != null) {
			player.seekTo(0);
			start();
		}
	}

	public void release() {
		mListener = null;
		if (player != null) {
			player.removeListener(this);
			player.release();
			player = null;
		}
	}

	public void seekTo(long progress) {
		if (player != null) {
			player.seekTo(progress);
		}
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public int getTrackCount() {
		MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
		if (mappedTrackInfo != null) {
			for (int rendererIndex = 0; rendererIndex < mappedTrackInfo.getRendererCount(); rendererIndex++) {
				int trackType = mappedTrackInfo.getRendererType(rendererIndex);
				TrackGroupArray trackGroupArray = mappedTrackInfo.getTrackGroups(rendererIndex);
				if (trackType == C.TRACK_TYPE_AUDIO) {
					return trackGroupArray.length;
				}
			}
		}
		return 1;
	}

	public void switchTrack(int index) {
		MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
		if (mappedTrackInfo == null) return;
		if (trackSelector.getParameters() == null) {
			return;
		}
		DefaultTrackSelector.ParametersBuilder builder = trackSelector.getParameters().buildUpon();
		for (int rendererIndex = 0; rendererIndex < mappedTrackInfo.getRendererCount(); rendererIndex++) {
			int trackType = mappedTrackInfo.getRendererType(rendererIndex);
			TrackGroupArray trackGroupArray = mappedTrackInfo.getTrackGroups(rendererIndex);
			if (trackType == C.TRACK_TYPE_AUDIO) {
				builder.setSelectionOverride(rendererIndex, mappedTrackInfo.getTrackGroups(rendererIndex),
						new DefaultTrackSelector.SelectionOverride(index, 0));
			}
		}
		trackSelector.setParameters(builder);
	}

	//EventListener
	@Override
	public void onLoadingChanged(boolean isLoading) {
		if (mListener != null)
			mListener.onLoadingChanged(isLoading);
	}

	@Override
	public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
		//Logger.e("------> onPlayerStateChanged playWhenReady=" + playWhenReady + "; playbackState=" + playbackState);
		if (playWhenReady && playbackState == Player.STATE_READY) {
			isPlaying = true;
		} else {
			isPlaying = false;
		}
		if (mListener != null)
			mListener.onPlayerStateChanged(playWhenReady, playbackState);
	}

	@Override
	public void onPlayerError(ExoPlaybackException error) {
		//Logger.e("------> onPlayerError error=" + error.toString());
		if (mListener != null)
			mListener.onPlayerError(error);
	}
}
