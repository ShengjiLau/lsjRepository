package com.lcdt.login.web;

/**
 * Created by ss on 2017/8/9.
 */


import com.lcdt.login.exception.CaptchaTimeExpireException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * @ClassName: CaptchaUtil
 * @Description: 关于验证码的工具类
 * @author 无名
 * @date 2016-5-7 上午8:33:08
 * @version 1.0
 */
public final class CaptchaUtil{

	public static final String SESSION_KEY_CAPTCHA = "SESSION_KEY_CAPTCHA";

	private CaptchaUtil(){}

	/*
	 * 随机字符字典
	 */
	private static final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8',
			'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
			'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/*
	 * 随机数
	 */
	private static Random random = new Random();

	/*
	 * 获取6位随机数
	 */
	private static String getSessionKeyCaptcha()
	{
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < 4; i++)
		{
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}

	/*
	 * 获取随机数颜色
	 */
	private static Color getRandomColor()
	{
		return new Color(random.nextInt(255),random.nextInt(255),
				random.nextInt(255));
	}

	/*
	 * 返回某颜色的反色
	 */
	private static Color getReverseColor(Color c)
	{
		return new Color(255 - c.getRed(), 255 - c.getGreen(),
				255 - c.getBlue());
	}

	public static String outputCaptcha(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		response.setContentType("image/jpeg");


		String randomString = getSessionKeyCaptcha();

		CaptchaAndTime captchaAndTime = new CaptchaAndTime();
		captchaAndTime.setCaptcha(randomString);
		captchaAndTime.setCreateTime(System.currentTimeMillis()/1000);

		request.getSession(true).setAttribute(SESSION_KEY_CAPTCHA,captchaAndTime);

		int width = 80;
		int height = 30;

		Color color = getRandomColor();
		Color reverse = getReverseColor(color);

		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		g.setColor(color);
		g.fillRect(0, 0, width, height);
		g.setColor(reverse);
		g.drawString(randomString, 18, 20);
		for (int i = 0, n = random.nextInt(100); i < n; i++)
		{
			g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
		}
		try {
			ImageIO.write(bi, "JPEG", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
		}

		// 转成JPEG格式
//		ServletOutputStream out = response.getOutputStream();
//		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//		encoder.encode(bi);
//		out.flush();

		return randomString;
	}


	/**
	 * 返回验证码，如果验证码过期 抛出异常
	 * @param request
	 * @return
	 * @throws CaptchaTimeExpireException
	 */
	public static String getCaptchaString(HttpServletRequest request) throws CaptchaTimeExpireException {
		CaptchaAndTime captchaAndTime = request.getSession() == null ? null : (CaptchaAndTime) request.getSession().getAttribute(CaptchaUtil.SESSION_KEY_CAPTCHA);
		if (captchaAndTime == null) {
			throw new CaptchaTimeExpireException();
		}
		boolean isTimeExpire = isTimeExpire(captchaAndTime.getCreateTime(), 30, TimeUnit.SECONDS);
		if (isTimeExpire) {
			throw new CaptchaTimeExpireException();
		}else {
			return captchaAndTime.getCaptcha();
		}
	}


	/**
	 * 判断beginTimeStamp 是否已过期
	 * @param beginTimeStamp
	 * @param period
	 * @return
	 */
	public static boolean isTimeExpire(long beginTimeStamp, long period, TimeUnit timeUnit){

		long l = System.currentTimeMillis() / 1000 - beginTimeStamp;
		long convert = TimeUnit.MILLISECONDS.convert(period, timeUnit);
		if (l >= convert){
			return true; //已过期
		}else{
			return false;
		}
	}

}
