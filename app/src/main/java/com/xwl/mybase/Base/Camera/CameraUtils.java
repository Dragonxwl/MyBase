package com.xwl.mybase.Base.Camera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.xwl.mybase.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.xwl.mybase.Base.IntentRequestCode.CAMERA_REQUEST_CODE_PHOTO_ABRIDGE;
import static com.xwl.mybase.Base.IntentRequestCode.CAMERA_REQUEST_CODE_PHOTO_SELF;

public class CameraUtils {

	private AppCompatActivity activity;
	private Uri mImageUri;//原图路径

	public CameraUtils(AppCompatActivity activity) {
		this.activity = activity;
	}

	/**
	 * 启动相机拍照返回缩略图
	 * */
	public void takePhotoAbridge() {
		Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
		if(takePhotoIntent.resolveActivity(activity.getPackageManager())!=null){//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
			activity.startActivityForResult(takePhotoIntent,CAMERA_REQUEST_CODE_PHOTO_ABRIDGE);//启动相机
		}
	}

	/**
	 * 启动相机拍照返回原图
	 * */
	public void takePhotoSelf(){
		Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机的Intent
		if(takePhotoIntent.resolveActivity(activity.getPackageManager())!=null){//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
			File imageFile = createImageFile();//创建用来保存照片的文件
			if(imageFile!=null){
				if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
					/*7.0以上要通过FileProvider将File转化为Uri*/
					mImageUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileProvider",imageFile);
				}else {
					/*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
					mImageUri = Uri.fromFile(imageFile);
				}
				takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);//将用于输出的文件Uri传递给相机
				activity.startActivityForResult(takePhotoIntent, CAMERA_REQUEST_CODE_PHOTO_SELF);//打开相机
			}
		}
	}

	/**
	 * 创建用来存储图片的文件，以时间来命名就不会产生命名冲突
	 * @return 创建的图片文件
	 */
	@SuppressLint("SimpleDateFormat")
	private File createImageFile() {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_"+timeStamp+"_";
		File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File imageFile = null;
		try {
			imageFile = File.createTempFile(imageFileName,".jpg",storageDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageFile;
	}

	/**
	 * 判断是否是相册值
	 * */
	public Boolean isCamera(int requestCode) {
		return requestCode == CAMERA_REQUEST_CODE_PHOTO_ABRIDGE
				|| requestCode == CAMERA_REQUEST_CODE_PHOTO_SELF;
	}

	/**
	 * 获取返回图片
	 * @return 图片bitmap
	 * */
	public Bitmap getBitmap(int requestCode,int resultCode,Intent data) {
		Bitmap bitmap = null;
		if(requestCode == CAMERA_REQUEST_CODE_PHOTO_ABRIDGE && resultCode == RESULT_OK){
			/*缩略图信息是储存在返回的intent中的Bundle中的，
			 * 对应Bundle中的键为data，因此从Intent中取出
			 * Bundle再根据data取出来Bitmap即可*/
			Bundle extras = data.getExtras();
			if (extras != null) {
				bitmap = (Bitmap) extras.get("data");
			}
		} else if (requestCode == CAMERA_REQUEST_CODE_PHOTO_SELF && resultCode == RESULT_OK) {
			try {
				/*如果拍照成功，将Uri用BitmapFactory的decodeStream方法转为Bitmap*/
				bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(mImageUri));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		destroy();
		return bitmap;
	}

	/**
	 * 销毁 防止内存泄漏
	 * */
	public void destroy() {
		activity = null;
		mImageUri = null;
	}

}
