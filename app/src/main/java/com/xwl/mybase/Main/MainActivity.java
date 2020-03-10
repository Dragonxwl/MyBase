package com.xwl.mybase.Main;

import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import com.xwl.mybaselib.Base.BaseActivity;
import com.xwl.mybase.BezierShow.BezierActivity;
import com.xwl.mybase.CameraShow.CameraShowActivity;
import com.xwl.mybase.R;
import com.xwl.mybaselib.Base.BaseApplication;

public class MainActivity extends BaseActivity {

	// 退出按钮二次点击时间间隔
	private long exitTime = 0;
	public Button btn_camera;
	public Button btn_bezier;

	@Override
	public void initUI() {
		super.initUI();
		setContentView(R.layout.activity_main);

		btn_camera = findViewById(R.id.btn_camera);
		btn_bezier = findViewById(R.id.btn_bezier);
	}

	@Override
	public void initData() {
		super.initData();
		btn_camera.setOnClickListener(v -> {
			Intent intent = new Intent(MainActivity.this, CameraShowActivity.class);
			startActivity(intent);
		});

		btn_bezier.setOnClickListener(v -> {
			Intent intent = new Intent(MainActivity.this, BezierActivity.class);
			startActivity(intent);
		});
	}

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - exitTime > 2000) {
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			super.onBackPressed();
			BaseApplication.exit();
		}
	}
}
