

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.omg.PortableInterceptor.ObjectIdHelper;

import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class WebCam 
{

	public static void main(String[] args)
	{
		new WebCam().start1();
	}
	
	
	JFrame f=new JFrame("Server");
	BufferedImage img1;
	Image img2;
	OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
	Socket s;
	ObjectOutputStream oops;
	ObjectInputStream oips;
	int pixelImg[];
	int pixelImgOutput[];
	public void start1()
	{
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setSize(700,400);
		JPanel p=new JPanel()
		{
			public void paint(Graphics g)
			{	
				if(img2==null)
				{
					return;
				}
				g.drawImage(img2, 0, 0, getWidth(), getHeight(),null);
			}
		};
		f.add(p);
		f.setVisible(true);
		try
		{
			ServerSocket ss=new ServerSocket(7777);

			s=ss.accept();
			oops=new ObjectOutputStream(s.getOutputStream());
			oips=new ObjectInputStream(s.getInputStream());

			grabber.start();
			IplImage img;

			PixelGrabber pix=null;
			int w=200,h=200;

			while(true)
			{

			//	System.out.println(127);
				img = grabber.grab();
			//	System.out.println(167);
				if (img != null) 
				{
				//	System.out.println(1);
					img1=img.getBufferedImage();
				//	if(img1==null)
				//		System.out.println(444);

					pixelImg=new int[w*h];
					try
					{
					 pix= new PixelGrabber(img1, 0, 0, w,	h	, pixelImg, 0,w);
					 pix.grabPixels();
					//		System.out.println(pixelImg+"1223154");
					}
					catch(InterruptedException e)
					{
					//	System.out.println("hell");
					}
					

			//		System.out.println(3);
					oops.writeObject(pixelImg);
			//		System.out.println(3232);
					pixelImgOutput=(int[])oips.readObject();
					img2=f.createImage(new MemoryImageSource(w, h, pixelImgOutput	, 0, w));
					if(img2!=null);
					//	System.out.println(2);
					f.repaint();

				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}

	}
}