package com.xwl.mybase.Base.Permission;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;

import com.xwl.mybase.Base.Camera.CameraPermissionInterface;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


/**
 * 继承接口的Activity或者Fragment需要@RuntimePermissions
 * RuntimePermissions	✓	注册Activity或Fragment处理权限
 * NeedsPermission    ✓	注释执行需要一个或多个权限的操作的方法
 * OnShowRationale 注释一个解释为什么需要权限的方法。它传入一个PermissionRequest对象，该对象可用于在用户输
 * 入时继续或中止当前的权限请求。如果不为该方法指定任何参数，则编译器将生成
 * process${NeedsPermissionMethodName}ProcessRequest和
 * cancel${NeedsPermissionMethodName}ProcessRequest。
 * 您可以使用这些方法代替PermissionRequest（例如：with DialogFragment）
 * OnPermissionDenied 注释如果用户未授予权限则调用的方法
 * OnNeverAskAgain 注释一个方法，如果用户选择让设备“不再询问”权限，则调用该方法
 * */
@SuppressLint("Registered")
@RuntimePermissions
public class BasePermissionActivity extends AppCompatActivity implements CameraPermissionInterface {
	@Override
	public void CheckCamera() {
		BasePermissionActivityPermissionsDispatcher.NeedCameraWithPermissionCheck(this);
	}

	@Override
	@NeedsPermission(Manifest.permission.CAMERA)
	public void NeedCamera() {
	}

	@Override
	@OnShowRationale(Manifest.permission.CAMERA)
	public void showRationaleForRecord(PermissionRequest request) {

	}

	@Override
	@OnPermissionDenied(Manifest.permission.CAMERA)
	public void showRecordDenied() {

	}

	@Override
	@OnNeverAskAgain(Manifest.permission.CAMERA)
	public void onRecordNeverAskAgain() {

	}
}
