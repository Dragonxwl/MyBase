package com.xwl.mybaselib.Base.Camera;

import permissions.dispatcher.PermissionRequest;

public interface CameraPermissionInterface {

	/**
	 * 相机权限检查
	 */
	void CheckCamera();

	/**
	 * 需要相机权限的操作
	 */
	void NeedCamera();

	/**
	 * 解释为什么需要权限
	 */
	void showRationaleForRecord(final PermissionRequest request);

	/**
	 * 申请权限被拒绝
	 */
	void showRecordDenied();

	/**
	 * 再次申请权限弹出框
	 */
	void onRecordNeverAskAgain();
}
