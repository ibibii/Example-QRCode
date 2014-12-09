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
	 * 生成二维码
	 * 
	 * @param content
	 *            二维码中包含的内容
	 * @param width
	 *            二维码图片的宽
	 * @param height
	 *            二维码图片的高
	 * @return
	 */
	public Bitmap createQRCodeBitmap(String content) {
		// 用于设置QR二维码参数
		Hashtable<EncodeHintType, Object> qrParam = new Hashtable<EncodeHintType, Object>();
		// 设置QR二维码的纠错级别——这里选择最高H级别
		qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置编码方式
		qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		// 设定二维码里面的内容，这里我采用我微博的地址
		// String content = "sinaweibo://userinfo?uid=2568190010";

		// 生成QR二维码数据——这里只是得到一个由true和false组成的数组
		// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
					BarcodeFormat.QR_CODE, width, height, qrParam);

			// 开始利用二维码数据创建Bitmap图片，分别设为黑白两色
			int w = bitMatrix.getWidth();
			int h = bitMatrix.getHeight();
			int[] data = new int[w * h];

			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					if (bitMatrix.get(x, y))
						data[y * w + x] = 0xff000000;// 黑色
					else
						data[y * w + x] = -1;// -1 相当于0xffffffff 白色
				}
			}

			// 创建一张bitmap图片，采用最高的效果显示
			Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			// 将上面的二维码颜色数组传入，生成图片颜色
			bitmap.setPixels(data, 0, w, 0, 0, w, h);
			return bitmap;
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 在二维码上绘制头像<br />
	 * 1、如果要采用在二维码中添加头像，那么生成的二维码最好采用最高等级H级别的纠错能力，目的有两个：一是增加二维码的正确识别能力；
	 * 二是扩大二维码数据内容的大小。<br />
	 * 2、头像大小最好不要超过二维码本身大小的1/5，而且只能放在正中间部位。这是由于二维码本身结构造成的。
	 * 所以说新浪微博的二维码只是合理的利用了规则而已。<br />
	 * 3、如果要仿照腾讯微信，在二维码边上增加装饰框，记得一定要在装饰框和二维码之间留出白边，这是为了二维码可识别。
	 * 
	 * @param qr
	 *            二维码
	 * @param portrait
	 *            头像资源
	 */
	public void createQRCodeBitmapWithPortrait(Bitmap qr, Bitmap portrait) {
		// 头像图片的大小
		int portrait_W = portrait.getWidth();
		int portrait_H = portrait.getHeight();

		// 设置头像要显示的位置，即居中显示
		int left = (width - portrait_W) / 2;
		int top = (height - portrait_H) / 2;
		int right = left + portrait_W;
		int bottom = top + portrait_H;
		Rect rect1 = new Rect(left, top, right, bottom);

		// 取得qr二维码图片上的画笔，即要在二维码图片上绘制我们的头像
		Canvas canvas = new Canvas(qr);

		// 设置我们要绘制的范围大小，也就是头像的大小范围
		Rect rect2 = new Rect(0, 0, portrait_W, portrait_H);
		// 开始绘制
		canvas.drawBitmap(portrait, rect2, rect1, null);
	}

}
