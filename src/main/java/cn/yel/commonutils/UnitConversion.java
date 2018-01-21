package cn.yel.commonutils;

public class UnitConversion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getNDigits("100",10));
	}
	
	/**
	 * 单位byte转换成GB，保持两位小数
	 * @param num_byte
	 * @return
	 */
	public static String byteToGb(String num_byte) {
		if(num_byte==null || "".equals(num_byte)) return "";
		Double double1 = Double.parseDouble(num_byte);
		Double res = double1 / (double) (1024*1024*1024);
		String result = String.format("%.2f",res);
		return result+"";
	}
	
	/**
	 * 单位byte转换成MB,保持两位小数
	 * @param num_byte
	 * @return
	 */
	public static String byteToMb(String num_byte) {
		if(num_byte==null || "".equals(num_byte)) return "";
		Double double1 = Double.parseDouble(num_byte);
		Double res = double1 / (double) (1024*1024);
		String result = String.format("%.2f",res);
		return result+"";
	}
	
	/**
	 * 单位bps转换成Kbps,保留两位小数
	 * @param num_bps String类型的bps数据
	 * @return 
	 */
	public static String bpsToKbps(String num_bps) {
		if(num_bps==null || "".equals(num_bps)) return null;
		Double double1 = Double.parseDouble(num_bps);
		Double res = double1 / (double) (1024);
		String result = String.format("%.2f",res);
		return result+"";
	}
	
	/**
	 * 保留N位有效数字
	 * @param num
	 * @param N
	 * @return String类型的数字
	 */
	public static String getNDigits(String num, int N) {
		if(num==null || "".equals(num)) return "";
		Double double1 = Double.parseDouble(num);
		String result = String.format("%."+N+"f",double1);
		return result+"";
	}
}
