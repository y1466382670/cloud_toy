package com.yu.util;

import java.util.UUID;

/**
 * @Title 
 * @Description UuidUtil
 * @author yanzhiyu
 * @date 2019年6月12日 17:00:20
 * @version V1.0.0
 */
public class UuidUtil {
	
	/**
	 * 生成32位uuid
	 */
	public static String generate32Uuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}


	public static String getUUID() {
		return getUUID(UUID.randomUUID());
	}
	/**
	 * 返回大写的uuid,长度32(81BE245897EA47329E685259FEB2D784)
	 *
	 * @return
	 */
	public static String getUUID(UUID uuid) {
		StringBuilder sb = new StringBuilder();
		sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
		sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
		sb.append(digits(uuid.getMostSignificantBits(), 4));
		sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
		sb.append(digits(uuid.getLeastSignificantBits(), 12));
		return sb.toString();
	}
	/**
	 * Returns val represented by the specified number of hex digits.
	 */
	private static String digits(long val, int digits) {
		long hi = 1L << (digits * 4);
		return Long.toHexString(hi | (val & (hi - 1))).substring(1).toUpperCase();
	}


	/**
	 * 数字转汉字
	 * @param src
	 * @return
	 */
	public static String int2chineseNum(int src) {
		final String num[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
		final String unit[] = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
		String dst = "";
		int count = 0;
		while(src > 0) {
			dst = (num[src % 10] + unit[count]) + dst;
			src = src / 10;
			count++;
		}
		return dst.replaceAll("零[千百十]", "零").replaceAll("零+万", "万")
				.replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
				.replaceAll("零+", "零").replaceAll("零$", "");
	}

}
