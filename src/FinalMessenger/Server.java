package FinalMessenger;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class Server implements Runnable
{

	static OpenCVFrameGrabber g;
	static File file;
	//static JFrame f;
	static JPanel p;
	static BufferedImage img;
	static ServerSocket s;
	static Socket s1;
	static ObjectOutputStream oops;
	static ObjectInputStream oips;
	BufferedImage img2;
	Thread t;
//	public static void main(String args[])
//	{
//		Server c=new Server();
//		c.createcamera();
//		while(true)
//		{	
//		c.capture();
//		c.send();
//		//c.read();
//		}
//	}
	
	public void run()
	{
		while(true)
		{
			capture();
			send();
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
		try {
			s=new ServerSocket(7771);
			s1=s.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			oops=new ObjectOutputStream(s1.getOutputStream());
			oips=new ObjectInputStream(s1.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//f=new JFrame("server");
		p=new JPanel(){
			public void paint(Graphics g)
			{
				g.drawImage(img2, 0, 0, 700, 400, null);
			}
		};
		file=new File("E:/Test/image.png");
		g=new OpenCVFrameGrabber(0);
		
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
		try {
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Sender.f.add(p);
//		Sender.f.repaint();

		
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
	
	
	public void read()
	{
	
			
			try {
				img2=ImageIO.read((File)oips.readObject());

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("class not found");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ioexception ");
			}
			p.repaint();
			Sender.f.add(p);
			Sender.f.repaint();


	}
	
}
