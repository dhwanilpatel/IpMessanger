

	import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import static com.googlecode.javacv.cpp.opencv_highgui.*;

public class CaptureImage 
{

	static JFrame f;
	static BufferedImage img1;
	static final OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
	    private static void captureFrame() 
	    {
	        // 0-default camera, 1 - next...so on
	        
	        try {
	            
	            IplImage img = grabber.grab();
	            if (img != null) {
	                
	             img1=img.getBufferedImage();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    public static void main(String[] args) 
	    {
	    	f=new JFrame("webcam");
	    	f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
	    	f.setSize(700,400);
	    	f.setVisible(true);
	    	f.add(new JPanel(){
	    		public void paint(Graphics g)
	    		{
	    			g.drawImage(img1,0,0,getWidth(),getHeight(),null);
	    		}
	    	});
	    	try {
				grabber.start();
			} catch (com.googlecode.javacv.FrameGrabber.Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	while(true)
	    	{
	    		captureFrame();
	    		f.repaint();
	    		try
	    		{
	    	//		Thread.sleep(0);
	    		}
	    		catch(Exception e)
	    		{
	    			System.exit(0);
	    		}
	    	}
	    }
	}
