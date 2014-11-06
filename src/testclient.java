import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class testclient
{

	static Socket s;
	static JFrame f;
	static JFrame tmp;
	static JPanel p;
	static ObjectInputStream oips;
	static Image img;
	static int[] pixel;
	int w=100,h=100;
	public static void main(String args[])
	{
		f=new JFrame("client");
		p=new JPanel(){
			public void paint(Graphics g)
			{
				g.drawImage(img, 0, 0, 700, 400, null);
			}
		};
		f.add(p);
		
		try {
			s=new Socket("127.0.0.1",7777);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oips=new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//tmp.setSize(700,400);
		//tmp.setDefaultCloseOperation(tmp.EXIT_ON_CLOSE);
		testclient t=new testclient();
			//while(true)
		//tmp.setVisible(true);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setSize(700,400);
		
				t.test();
				//tmp.repaint();
				try {
				//	Thread.sleep(10);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
		
			
	}
	
	public void test()
	{
		
		while(true)
		{
		
	
		try {
			//tmp=(JFrame)oips.readObject();
			pixel=(int[])oips.readObject();
			//System.out.println(tmp.toString());
		
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		img= f.createImage(new MemoryImageSource(w, h,pixel, 0,w));
		//f.setContentPane(tmp);
		p.repaint();
		f.repaint();
		
		f.setVisible(true);
		//tmp.setSize(1000,800);
		//tmp.setVisible(true);
		}
	}
}
