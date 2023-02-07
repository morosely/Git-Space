package fills.tools.md5;  


import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * 
 * @ClassName: MD5Util.java
 * @Description: md5工具
 * @author: ysf
 * @date: 2021年3月5日 上午9:59:46 
 *
 * Modification History:
 * Date         Author         Description
 * 2021年3月5日      ysf             md5工具
 */
public class MD5Util {

	/**
	 * 
	 * @Function: MD5Util.java
	 * @Description: Md5加密byte[]
	 *
	 * @param: data
	 * @param:@return
	 * @return：String
	 *
	 * @author: ysf
	 * @date: 2021年3月5日 上午10:00:20 
	 *
	 * Modification History:
	 * Date         Author      Description
	 * 2021年3月5日      ysf          Md5加密byte[]
	 */
    public static String md5Hex(byte[] data) {
        return DigestUtils.md5Hex(data);
    }

    /**
     * 
     * @Function: MD5Util.java
     * @Description: Md5加密String
     *
     * @param: data
     * @param:@return
     * @return：String
     *
     * @author: ysf
     * @date: 2021年3月5日 上午10:00:45 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月5日      ysf          Md5加密String
     */
    public static String md5Hex(String data) {
        return DigestUtils.md5Hex(data);
    }
    
    /**
     * 
     * @Function: MD5Util.java
     * @Description: Md5加密String
     *
     * @param: data
     * @param:@return
     * @return：String
     *
     * @author: ysf
     * @date: 2021年3月5日 上午10:00:45 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月5日      ysf          Md5加密String
     */
    public static String md5(String buffer){
        String string  = null;
        char hexDigist[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(buffer.getBytes());
            byte[] datas = md.digest(); //16个字节的长整数
            
            char[] str = new char[2*16];
            int k = 0;
            
            for(int i=0;i<16;i++){
              byte b   = datas[i];
              str[k++] = hexDigist[b>>>4 & 0xf];//高4位
              str[k++] = hexDigist[b & 0xf];//低4位
            }
            string = new String(str);
        } catch (Exception e){
        }
        return string;
    }
    
    /**
     * 
     * @Function: MD5Util.java
     * @Description: Md5加密byte[]
     *
     * @param: data
     * @param:@return
     * @return：String
     *
     * @author: ysf
     * @date: 2021年3月8日 下午5:05:45 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月8日      ysf          Md5加密byte[]
     */
    public static String md5(byte[] data){
        String string  = null;
        char hexDigist[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(data);
            byte[] datas = md.digest(); //16个字节的长整数
            
            char[] str = new char[2*16];
            int k = 0;
            
            for(int i=0;i<16;i++){
              byte b   = datas[i];
              str[k++] = hexDigist[b>>>4 & 0xf];//高4位
              str[k++] = hexDigist[b & 0xf];//低4位
            }
            string = new String(str);
        } catch (Exception e){
        }
        return string;
    }
}
