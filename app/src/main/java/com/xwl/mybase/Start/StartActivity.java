package com.xwl.mybase.Start;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.WindowManager;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Player;
import com.gyf.immersionbar.BarHide;
import com.xwl.mybaselib.Base.BaseActivity;
import com.xwl.mybaselib.Base.IsTablet.UiUtil;
import com.xwl.mybase.Main.MainActivity;
import com.xwl.mybase.R;
import com.xwl.mybase.video.ExoVideoView;
import com.xwl.mybase.video.PlayerManager;

import static android.view.View.VISIBLE;

public class StartActivity extends BaseActivity {

	private ExoVideoView videoView;

	@Override
	public void initUI() {
		super.initUI();
		// 防止点击桌面再次启动问题
		if (!isTaskRoot()) {
			finish();
			return;
		}

		// 判断是收集还是平板决定横竖屏
		if (UiUtil.isTablet()) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			mImmersionBar.transparentBar().init();
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			mImmersionBar.transparentBar().statusBarDarkFont(true).init();
		}

		//隐藏虚拟按键，并且全屏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		setContentView(R.layout.activity_start);

		videoView = findViewById(R.id.videoView);

	}

	@Override
	public void initData() {
		super.initData();
		playStartVideo();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mImmersionBar.fullScreen(true).hideBar(BarHide.FLAG_HIDE_BAR).init();
	}

	/**
	 * 播放开始动画
	 */
	public void playStartVideo() {
		PlayerManager.getInstance().initRaw(R.raw.start_video_phone, false);
		videoView.setPlayer(PlayerManager.getInstance().getPlayer());
		PlayerManager.getInstance().setListener(new PlayerManager.PlayerListener() {
			@Override
			public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
				if (playbackState == Player.STATE_READY) {
					videoView.setVisibility(VISIBLE);
					PlayerManager.getInstance().start();
				}
				if (playbackState == Player.STATE_ENDED) {
					Intent intent = new Intent(StartActivity.this, MainActivity.class);
					startActivity(intent);
				}
			}

			@Override
			public void onLoadingChanged(boolean isLoading) {

			}

			@Override
			public void onPlayerError(ExoPlaybackException error) {

			}
		});

	}
}
