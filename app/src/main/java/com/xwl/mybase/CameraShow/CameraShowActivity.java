package com.xwl.mybase.CameraShow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xwl.mybaselib.Base.BaseActivity;
import com.xwl.mybaselib.Base.Camera.CameraUtils;
import com.xwl.mybase.R;

public class CameraShowActivity extends BaseActivity {

	public ImageView imageView;
	// 相机模块
	public CameraUtils cameraUtils;
	// 点击按钮选项
	public int clickType;

	@Override
	public void initUI() {
		super.initUI();
		setContentView(R.layout.activity_camerashow);

		imageView = findViewById(R.id.imageView);
	}

	@Override
	public void initData() {
		super.initData();
	}

	@Override
	public void NeedCamera() {
		super.NeedCamera();
		// 初始化相机模块
		cameraUtils = new CameraUtils(CameraShowActivity.this);
		switch (clickType) {
			case 0:
				cameraUtils.takePhotoAbridge();
				break;
			case 1:
				cameraUtils.takePhotoSelf();
				break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 判断返回是属于相册的
		super.onActivityResult(requestCode, resultCode, data);
		if (cameraUtils.isCamera(requestCode)) {
			// 获取bitmap
			Bitmap bitmap = cameraUtils.getBitmap(requestCode, resultCode, data);
			if (bitmap != null) {
				imageView.setImageBitmap(bitmap);
			} else {
				Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
			}
			cameraUtils = null;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (cameraUtils != null) {
			cameraUtils.destroy();
			cameraUtils = null;
		}
	}

	/**
	 * 点击缩略图
	 * */
	public void ClickAbridge(View view) {
		clickType = 0;
		CheckCamera();
	}

	/**
	 * 点击原图
	 * */
	public void ClickSelf(View view) {
		clickType = 1;
		CheckCamera();
	}
}
