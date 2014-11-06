package FinalMessenger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundReciver
{

	Socket s;
	InputStream oips;
	File media=new File("E:/Test/rec.wav");
	public static void main(String args[])
	{
		SoundReciver r=new SoundReciver();
		r.createMedia();
		while(true)
		{
			r.playMedia();
			
		}
	}
	
	public void createMedia()
	{
		try {
			System.out.println("Socket created");
			s=new Socket("127.0.0.1",7770);
			System.out.println("Socket created");
			oips=(s.getInputStream());
			System.out.println("is created");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void playMedia()
	{
		byte[] tmp;
		Clip c=null;
		int a;
		char[] cha;
		AudioInputStream audio=null;
			System.out.println("4444");
			 int bytesRead;
			    int current = 0;
			    FileOutputStream fos = null;
			    BufferedOutputStream bos = null;
			    try {
			    	// receive file
			      byte [] mybytearray  = new byte [99999999];
			      InputStream is = s.getInputStream();
			      fos = new FileOutputStream(media);
			      bos = new BufferedOutputStream(fos);
			      bytesRead = is.read(mybytearray,0,mybytearray.length);
			      current = bytesRead;

			      do {
			         bytesRead = is.read(mybytearray, current, 1);
			         if(bytesRead >= 0) current += bytesRead;
			      } while(bytesRead > -1);
			      
			      bos.write(mybytearray, 0 , current);
			      bos.flush();
			      System.out.println("File "+" downloaded (" + current + " bytes read)");
			    if (fos != null) fos.close();
			    
			    }
			    catch(Exception e)
			    {
			    	e.printStackTrace();
			    }

			  
			try {
				audio=AudioSystem.getAudioInputStream(media);
			} catch (UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 
		System.out.println("File recived ");
		
		try {
			c=AudioSystem.getClip();
			c.open(audio);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Start playing");
		c.start();
		
		
	}
}
