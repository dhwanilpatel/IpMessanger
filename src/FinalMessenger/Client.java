package FinalMessenger;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class Client implements Runnable
{

	static Socket s;
	static ObjectInputStream oips;
	static ObjectOutputStream oops;
	static JFrame f;
	JPanel p;
	Image img;
	File file;
	OpenCVFrameGrabber g;
	Thread t; 
	
//	public static void main(String args[])
//	{
//		Client c=new Client();
//		c.createcamera();
//		while(true)
//		{	
//			//c.capture();
//			//c.send();
//			c.read();
//		
//		}
//	}
	
	public void run()
	{
		while(true)
		{
			//capture();
			//send();
			read();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void startRecording()
	{
		t=new Thread(this);
		t.start();
	}
	
	
	public void createcamera()
	{
		//f=new JFrame("client");
		file=new File("E:/Test/image.png");
		p=new JPanel(){
			public void paint(Graphics g)
			{
				g.drawImage(img, 0, 0, 700, 400,null);
			}
		};
		try {
			s=new Socket("127.0.0.1",7771);
			System.out.println("connected");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			oips=new ObjectInputStream(s.getInputStream());
			oops=new ObjectOutputStream(s.getOutputStream());
			System.out.println("ready to recieve");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		file=new File("E:/Test/image.png");
//		g=new OpenCVFrameGrabber(0);
	}
	
	public void capture()
	{
		
			IplImage img1 = null;
			try {
				g.start();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				img1=g.grab();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			img=img1.getBufferedImage();
//			try {
//				ImageIO.write(img, "png", file);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
	}
	
	public void read()
	{
		
		try {
			img=ImageIO.read((File)oips.readObject());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("class not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ioexception ");
		}
		
		Reciver.f.add(p);
		Reciver.f.repaint();
	}
	
	public void send()
	{
		try {
				oops.writeObject(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
