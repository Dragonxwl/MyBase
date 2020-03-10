package com.xwl.mybaselib.Base;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.xwl.mybaselib.Base.Permission.BasePermissionActivity;

/**
 * BasePermissionActivity 实现权限接口
 * */
@SuppressLint("Registered")
public class BaseActivity extends BasePermissionActivity {

	// 状态栏
	protected ImmersionBar mImmersionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 添加每一个Activity到List中
		BaseApplication.addActivity(this);

		// 状态栏
		mImmersionBar = ImmersionBar.with(this);
		mImmersionBar.init();

		initUI();
		initData();
	}

	/**
	 * 初始化UI
	 * */
	public void initUI() {

	}

	/**
	 * 初始化数据
	 * */
	public void initData() {

	}

	@Override
	protected void onDestroy() {
		// 移除每一个Activity
		BaseApplication.removeActivity(this);
		super.onDestroy();
	}
}
