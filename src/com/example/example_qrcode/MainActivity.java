package com.example.example_qrcode;

import com.qf.teach.framework.zxing.lib.activity.MipcaActivityCapture;
import com.qf.teach.framework.zxing.lib.qrcode.util.QRCodeUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {
	/**
	 * 问题:imgv 1.放置生成的二维码,2.扫描到的二维码.出错.要么生成,要么扫描,只能完成一个.
	 * 问题:扫描的窗口太小.改了activity_capture.xml中的com.qf.teach.framework.zxing.lib.qrcode.view.ViewfinderView,无效.
	 */
	private ImageView imgv;
	private EditText et;
	private QRCodeUtil util;
	private final static int SCANNIN_GREQUEST_CODE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imgv =(ImageView) findViewById(R.id.imgv);
		et = (EditText) findViewById(R.id.et);
		util = new QRCodeUtil(700, 700);
	}
	/**
	 * button监听.
	 * @param view  2014年12月9日 下午12:33:45
	 */
	public void btnClick(View view){
		switch(view.getId()){
		case R.id.btn_create:
			String content = et.getText().toString();
			Bitmap codeBitmap = util.createQRCodeBitmap(content);
			imgv.setImageBitmap(codeBitmap);
			break;
		case R.id.btn_scanner:
			Intent intent = new Intent(MainActivity.this,MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1&& resultCode==RESULT_OK){
			Bundle bundle = data.getExtras();
			et.setHint(bundle.getString("result"));
			imgv.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
		}
	}
}
