


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.WritableRaster;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.ServerCloneException;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.OpenCVFrameGrabber;

public class WebChat2 
{
     OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
     Socket s;
     ObjectOutputStream oops;
     ObjectInputStream oips;
     
     frame f1,f2;
     BufferedImage img;
     
    class frame extends JFrame implements Serializable
    {

/**
* 
*/
private static final long serialVersionUID = 1L;

    }
     public static void main(String[] args)
     {
new WebChat2().start();
     }
     
     public void start()
     {
try 
{
s=new ServerSocket(7777).accept();
//s=new Socket("10.100.88.34",7777);
oops=new ObjectOutputStream(s.getOutputStream());
oips=new ObjectInputStream(s.getInputStream());
System.out.println("connected");

f1=new frame();
JPanel p=new JPanel()
{
public void paint(Graphics g)
{
g.drawImage(img, 0, 0, 700, 400,null);
}
};
f1.add(p);
f1.setDefaultCloseOperation(f1.EXIT_ON_CLOSE);
f1.setSize(700,400);
grabber.start();
IplImage img1;
while(true)
{
img1=grabber.grab();
img=img1.getBufferedImage();
p.revalidate();
f1.repaint();
oops.writeObject(f1);
try
{
f2=(frame)oips.readObject();
f2.setVisible(true);
}
catch (ClassNotFoundException e) {
// TODO Auto-generated catch block
e.printStackTrace();
System.exit(0);
}
}

} 
catch (IOException e)
{
// TODO Auto-generated catch block
e.printStackTrace();
System.exit(0);
}
catch (Exception e)
{
// TODO Auto-generated catch block
e.printStackTrace();
System.exit(0);
}
     }
}
