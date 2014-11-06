import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;


public class testserver 
{

	static JFrame f;
	static JPanel p;
	static BufferedImage img;
	static OpenCVFrameGrabber g=new OpenCVFrameGrabber(0);
	static ServerSocket s;
	static Socket s1;
	//static ObjectInputStream oips;
	static ObjectOutputStream oops;
	static JFrame f2;
	static int[] pixel;
	static int w=100,h=100;
	static PixelGrabber pg;		
	static File file;
	public static void main(String args[])
	{
		//f2=new JFrame();
		
		f=new JFrame("server");
		pixel=new int[w*h];
		p=new JPanel(){
			public void paint(Graphics g)
			{
				g.drawImage(img,0, 0,700, 400, null);
			}
		};
		try {
			s=new ServerSocket(7777);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Server not created");
		}
		try {
			s1=s.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Socket not created");
		}
		try {
			//oips=new ObjectInputStream(s1.getInputStream());
			oops=new ObjectOutputStream(s1.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("output stream not created");
		}
		try {
			g.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setSize(700,400);
		
		testserver t=new testserver();
		while(true)
		{
		t.start();
		pg=new PixelGrabber(img, 0, 0, w, h, pixel, 0,w);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.test();
		//f.repaint();
		f.add(p);
		p.repaint();
		//f.add(p);
		f.repaint();
		//System.out.println("main frame"+f.toString());
		//System.out.println("sended frame "+f2.toString());
		f.setVisible(true);
		
		}
		
		
	}
	
	public void start()
	{
		IplImage img1 = null;
		try {
			img1=g.grab();
	
		if(img1!=null)
		{
		img=img1.getBufferedImage();
		//System.out.println(img.toString());
		}
		//else
		//	System.out.println("hhgdhjg");
		
			//System.out.println(f2.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void test()
	{
		try {
			oops.writeObject(pixel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
