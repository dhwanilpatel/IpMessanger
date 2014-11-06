

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.omg.PortableInterceptor.ObjectIdHelper;

import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class Webcam1 
{
    
    public static void main(String[] args)
    {
new Webcam1().start1();
    }
    JFrame f=new JFrame("Client");
    JFrame f1=new  JFrame();
    IplImage img1;
    IplImage img2;
    OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
    Socket s;
    JPanel p1;
    ObjectOutputStream oops;
    ObjectInputStream oips;
    int w=200;
    int h=200;
    
    
    public void start1()
    {

System.out.println("hjgwd");
f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
f.setSize(w,h);
JPanel p=new JPanel()
{
public void paint(Graphics g)
{
if(img2==null)
{
return;
}
//System.out.println("sfjgduyf");
g.drawImage(img2.getBufferedImage(), 0, 0, getWidth(), getHeight(),null);
}
};
f1.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
f1.setSize(w,h);
f1.add(p);
//f.add(p);
f.setVisible(true);
try
{
ServerSocket ss=new ServerSocket(7777);
//s=new Socket("10.100.88.34",7777);
s=ss.accept();
oops=new ObjectOutputStream(s.getOutputStream());
oips=new ObjectInputStream(s.getInputStream());
grabber.start();
IplImage img ;
//    System.out.println("start");
while(true)
{
img2 = grabber.grab();
//    System.out.println("started1");    
                if (img2 != null) 
                {
                    p.repaint();
                    f1.repaint();
                    oops.writeObject(f1);
                    
                }
                else
                {
                    System.out.println("fjvn");
                }
                
                f=(JFrame)oips.readObject();
                
                f.repaint();
                f.setVisible(true);
               Thread.sleep(10);
}
}
catch(Exception e)
{
e.printStackTrace();
System.exit(0);
}
    
    }
}