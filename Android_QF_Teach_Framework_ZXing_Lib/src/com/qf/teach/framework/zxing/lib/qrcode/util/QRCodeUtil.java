package com.qf.teach.framework.zxing.lib.qrcode.util;

import java.util.Hashtable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil {
	private int width;
	private int height;

	public QRCodeUtil(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * ���ɶ�ά��
	 * 
	 * @param content
	 *            ��ά���а���������
	 * @param width
	 *            ��ά��ͼƬ�Ŀ�
	 * @param height
	 *            ��ά��ͼƬ�ĸ�
	 * @return
	 */
	public Bitmap createQRCodeBitmap(String content) {
		// ��������QR��ά�����
		Hashtable<EncodeHintType, Object> qrParam = new Hashtable<EncodeHintType, Object>();
		// ����QR��ά��ľ����𡪡�����ѡ�����H����
		qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// ���ñ��뷽ʽ
		qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		// �趨��ά����������ݣ������Ҳ�����΢���ĵ�ַ
		// String content = "sinaweibo://userinfo?uid=2568190010";

		// ����QR��ά�����ݡ�������ֻ�ǵõ�һ����true��false��ɵ�����
		// ����˳��ֱ�Ϊ���������ݣ��������ͣ�����ͼƬ��ȣ�����ͼƬ�߶ȣ����ò���
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
					BarcodeFormat.QR_CODE, width, height, qrParam);

			// ��ʼ���ö�ά�����ݴ���BitmapͼƬ���ֱ���Ϊ�ڰ���ɫ
			int w = bitMatrix.getWidth();
			int h = bitMatrix.getHeight();
			int[] data = new int[w * h];

			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					if (bitMatrix.get(x, y))
						data[y * w + x] = 0xff000000;// ��ɫ
					else
						data[y * w + x] = -1;// -1 �൱��0xffffffff ��ɫ
				}
			}

			// ����һ��bitmapͼƬ��������ߵ�Ч����ʾ
			Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			// ������Ķ�ά����ɫ���鴫�룬����ͼƬ��ɫ
			bitmap.setPixels(data, 0, w, 0, 0, w, h);
			return bitmap;
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �ڶ�ά���ϻ���ͷ��<br />
	 * 1�����Ҫ�����ڶ�ά�������ͷ����ô���ɵĶ�ά����ò�����ߵȼ�H����ľ���������Ŀ����������һ�����Ӷ�ά�����ȷʶ��������
	 * ���������ά���������ݵĴ�С��<br />
	 * 2��ͷ���С��ò�Ҫ������ά�뱾���С��1/5������ֻ�ܷ������м䲿λ���������ڶ�ά�뱾��ṹ��ɵġ�
	 * ����˵����΢���Ķ�ά��ֻ�Ǻ���������˹�����ѡ�<br />
	 * 3�����Ҫ������Ѷ΢�ţ��ڶ�ά���������װ�ο򣬼ǵ�һ��Ҫ��װ�ο�Ͷ�ά��֮�������ױߣ�����Ϊ�˶�ά���ʶ��
	 * 
	 * @param qr
	 *            ��ά��
	 * @param portrait
	 *            ͷ����Դ
	 */
	public void createQRCodeBitmapWithPortrait(Bitmap qr, Bitmap portrait) {
		// ͷ��ͼƬ�Ĵ�С
		int portrait_W = portrait.getWidth();
		int portrait_H = portrait.getHeight();

		// ����ͷ��Ҫ��ʾ��λ�ã���������ʾ
		int left = (width - portrait_W) / 2;
		int top = (height - portrait_H) / 2;
		int right = left + portrait_W;
		int bottom = top + portrait_H;
		Rect rect1 = new Rect(left, top, right, bottom);

		// ȡ��qr��ά��ͼƬ�ϵĻ��ʣ���Ҫ�ڶ�ά��ͼƬ�ϻ������ǵ�ͷ��
		Canvas canvas = new Canvas(qr);

		// ��������Ҫ���Ƶķ�Χ��С��Ҳ����ͷ��Ĵ�С��Χ
		Rect rect2 = new Rect(0, 0, portrait_W, portrait_H);
		// ��ʼ����
		canvas.drawBitmap(portrait, rect2, rect1, null);
	}

}
