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
	 * ����:imgv 1.�������ɵĶ�ά��,2.ɨ�赽�Ķ�ά��.����.Ҫô����,Ҫôɨ��,ֻ�����һ��.
	 * ����:ɨ��Ĵ���̫С.����activity_capture.xml�е�com.qf.teach.framework.zxing.lib.qrcode.view.ViewfinderView,��Ч.
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
	 * button����.
	 * @param view  2014��12��9�� ����12:33:45
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
