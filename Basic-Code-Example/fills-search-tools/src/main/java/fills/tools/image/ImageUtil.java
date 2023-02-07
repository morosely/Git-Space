package fills.tools.image;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Author ysf
 * @Description   图片处理工具 //TODO
 * @Param
 * @Date 2020/11/30 14:55
 * @return
 **/
@SuppressWarnings("restriction")
public class ImageUtil {
	
    /**
     * @Author ysf
     * @Description   图片byte[] 转 BufferedImage //TODO
     * @Param  [data]
     * @Date 2020/11/30 14:58
     * @return java.awt.image.BufferedImage
     **/
    public static BufferedImage byte2BufferedImage(byte[] data){
        InputStream buffIn = new ByteArrayInputStream(data, 0, data.length);
        try {
            return ImageIO.read(buffIn);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * @Author ysf
     * @Description   根据图片地址获取图片 //TODO
     * @Param  [imageUrl]
     * @Date 2020/11/30 14:10
     * @return java.awt.Image
     **/
    public static Image getImageByImageUrl(String imageUrl){
        return new ImageIcon(imageUrl).getImage();
    }

    /**
     * @Author ysf
     * @Description   根据图片地址获取图片 //TODO
     * @Param  [imageUrl]
     * @Date 2020/12/3 17:39
     * @return java.awt.image.BufferedImage
     **/
    public static BufferedImage getBufferedImageByImageUrl(String imageUrl){
        try {
            return ImageIO.read(new File(imageUrl));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * @Author ysf
     * @Description   改变图片大小 //TODO
     * @Param  [image, ratio]
     * @Date 2020/12/3 17:40
     * @return java.awt.image.BufferedImage
     **/
    public static BufferedImage resizeTmage(BufferedImage bufferedImage,double ratio){
        // 图片宽度
        int width = getWidthAndHeight(bufferedImage.getWidth(), ratio);
        // 图片高度
        int height = getWidthAndHeight(bufferedImage.getHeight(),ratio);
        Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = newImage.getGraphics();
        // 绘制处理后的图片
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
    
    /**
     * 
     * @Function: ImageUtil.java
     * @Description: 图片缩小
     *
     * @param: bufferedImage
     * @param: ratio
     * @param:@return
     * @return：BufferedImage
     *
     * @author: ysf
     * @date: 2021年3月16日 上午10:55:46 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月16日      ysf         图片缩小
     */
    public static BufferedImage zoomImage(BufferedImage bufferedImage,double ratio){
        Image image = bufferedImage.getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight(), Image.SCALE_SMOOTH);
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
        image = ato.filter(bufferedImage, null);
        // 图片宽度
        int width = getWidthAndHeight(bufferedImage.getWidth(), ratio);
        // 图片高度
        int height = getWidthAndHeight(bufferedImage.getHeight(),ratio);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = newImage.getGraphics();
        // 绘制处理后的图片
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    /**
     * @Author ysf
     * @Description   生成图片信息 //TODO
     * @Param  [bufferedImage, imageUrl]
     * @Date 2020/12/3 17:46
     * @return void
     **/
    public static void saveLocalImage(BufferedImage bufferedImage,String imageUrl){
        String suffix = imageUrl.substring(imageUrl.lastIndexOf(".")+1,imageUrl.length());
        try {
            ImageIO.write(bufferedImage, suffix, new File(imageUrl));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * 
     * @Function: ImageUtil.java
     * @Description: image转byte
     *
     * @param:@return
     * @return：String
     *
     * @author: ysf
     * @date: 2021年2月22日 下午6:03:46 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年2月22日      ysf         image转byte
     */
    public static byte[] image2byte(Image image){
    	 int width = (int)(image.getWidth(null)); 
         int height = (int)(image.getHeight(null)); 
         //将设置好的图片追加到BufferedImage中 
         BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
         Graphics2D graphics = bufferedImage.createGraphics(); 
         //重构图片 
         graphics.drawImage(image, 0, 0, width, height, null); 
         ByteArrayOutputStream bs = null;
         ImageOutputStream imOut = null;
         try {
        	 bs =new ByteArrayOutputStream(); 
        	 imOut = ImageIO.createImageOutputStream(bs);
             ImageIO.write(bufferedImage,"png",imOut);
             return bs.toByteArray();
		 } catch (Exception e) {
			// TODO: handle exception
		 }finally{
			 try {
				 if(bs!=null){
					 bs.close();
				 }
			 } catch (Exception e2) {
				// TODO: handle exception
			 }
			 try {
				 if(imOut!=null){
					 imOut.close();
				 }
			} catch (Exception e2) {
				// TODO: handle exception
			}
			 
		 }
         return null;
    }
    
    /**
     * 
     * @Function: ImageUtil.java
     * @Description: image 转 BufferedImage
     *
     * @param: image
     * @param:@return
     * @return：BufferedImage
     *
     * @author: ysf
     * @date: 2021年3月8日 下午4:25:14 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月8日      ysf          image 转 BufferedImage
     */
    public static BufferedImage image2BufferedImage(Image image){
    	int width = (int)(image.getWidth(null)); 
        int height = (int)(image.getHeight(null)); 
        //将设置好的图片追加到BufferedImage中 
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
        Graphics2D graphics = bufferedImage.createGraphics(); 
        //重构图片 
        graphics.drawImage(image, 0, 0, width, height, null); 
    	return bufferedImage;
    }
    
    
    /**
     * 
     * @Function: ImageUtil.java
     * @Description: 图片放大缩小
     *
     * @param: old
     * @param: o
     * @param:@return
     * @return：int
     *
     * @author: ysf
     * @date: 2021年3月8日 下午6:00:24 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月8日      ysf          图片放大缩小
     */
    public static int getWidthAndHeight(int old,double o){
    	return new BigDecimal(old).multiply(new BigDecimal(o)).intValue();
    }
    
    /**
     * 
     * @Function: ImageUtil.java
     * @Description: byte[] 转 base64
     *
     * @param: imageByte
     * @param:@return
     * @return：String
     *
     * @author: ysf
     * @date: 2021年3月9日 下午3:45:46 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月9日      ysf          byte[] 转 base64
     */
    public static String byte2Base64(byte[] imageByte){
    	BASE64Encoder encoder = new BASE64Encoder();
    	return encoder.encode(imageByte);
    }
    
    /**
     * 
     * @Function: ImageUtil.java
     * @Description: base64 转 byte[]
     *
     * @param: imageByte
     * @param:@return
     * @return：byte[]
     *
     * @author: ysf
     * @date: 2021年3月9日 下午3:46:39 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月9日      ysf          base64 转 byte[]
     */
    public static byte[] base642Byte(String imageByte){
    	BASE64Decoder decoder = new BASE64Decoder();
    	try {
			return decoder.decodeBuffer(imageByte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
    	return null;
    }
    
    /**
     * 
     * @Function: ImageUtil.java
     * @Description: byte 转 image
     *
     * @param: imageByte
     * @param:@return
     * @return：Image
     *
     * @author: ysf
     * @date: 2021年3月9日 下午4:03:40 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月9日      ysf          byte 转 image
     */
    public static Image byte2Image(byte[] imageByte){
    	return new ImageIcon(imageByte).getImage();
    }

    /**
     * 
     * @Function: ImageUtil.java
     * @Description: base64 转 image
     *
     * @param: base64
     * @param:@return
     * @return：Image
     *
     * @author: ysf
     * @date: 2021年3月9日 下午4:06:01 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月9日      ysf          base64 转 image
     */
    public static Image base642Image(String base64){
    	return byte2Image(base642Byte(base64));
    }
    
    /**
     * 
     * @Function: ImageUtil.java
     * @Description: image 转 base64
     *
     * @param: image
     * @param:@return
     * @return：String
     *
     * @author: ysf
     * @date: 2021年3月9日 下午4:36:05 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月9日      ysf          image 转 base64
     */
    public static String image2Base64(Image image){
    	return byte2Base64(image2byte(image));
    }
    
    /**
     * 
     * @Function: ImageUtil.java
     * @Description: BufferedImage 转  byte
     *
     * @param: bufferedImage
     * @param:@return
     * @return：byte[]
     *
     * @author: ysf
     * @date: 2021年3月15日 下午4:49:37 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月15日      ysf         BufferedImage 转  byte
     */
    public static byte[] bufferedImage2byte(BufferedImage bufferedImage){
    	ByteArrayOutputStream bs = null;
        ImageOutputStream imOut = null;
        try {
       	 	bs =new ByteArrayOutputStream(); 
       	 	imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bufferedImage,"png",imOut);
            return bs.toByteArray();
		 } catch (Exception e) {
			// TODO: handle exception
		 }finally{
			 try {
				 if(bs!=null){
					 bs.close();
				 }
			 } catch (Exception e2) {
				// TODO: handle exception
			 }
			 try {
				 if(imOut!=null){
					 imOut.close();
				 }
			} catch (Exception e2) {
				// TODO: handle exception
			}
		 }
    	return null;
    }
    
    /**
     * 
     * @Function: ImageUtil.java
     * @Description: BufferedImage 转 InputStream
     *
     * @param: image
     * @param:@return
     * @return：InputStream
     *
     * @author: ysf
     * @date: 2021年3月15日 下午5:34:56 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月15日      ysf         BufferedImage 转 InputStream
     */
    public  static InputStream bufferedImage2InputStream(BufferedImage image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream input =null;
        try {
            ImageIO.write(image, "png", os);
            input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
        }finally{
        	try {
				if(os!=null){
					os.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
        	try {
				if(input!=null){
					input.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
        }
        return null;
    }
    

}
