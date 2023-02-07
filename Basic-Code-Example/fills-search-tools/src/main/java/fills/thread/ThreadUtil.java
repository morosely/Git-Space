package fills.thread;

public class ThreadUtil {
	
	/**
	 * 
	 * @Function: ThreadUtil.java
	 * @Description: 时间停顿
	 *
	 * @param: time
	 * @return：void
	 *
	 * @author: ysf
	 * @date: 2021年3月5日 下午3:02:17 
	 *
	 * Modification History:
	 * Date         Author      Description
	 * 2021年3月5日      ysf          时间停顿
	 */
	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
