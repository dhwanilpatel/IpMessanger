

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;


public class Voicecha
{

    static final long RECORD_TIME = 1500;  // 1 minute
     
    // path of the wav file
    File wavFile;
   
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    TargetDataLine line;
    
    ServerSocket ss;
    Socket s;
    OutputStream os = null;

        public void test()
        {
     FileInputStream fis = null;
       BufferedInputStream bis = null;
       
    //   ServerSocket servsock = null;
     //  Socket sock = null;
       try {
        
          {
           System.out.println("Waiting...");
           try {
           //  sock = servsock.accept();
       //      System.out.println("Accepted connection : " + sock);
             // send file
             File myFile = new File ("E:/Test/RecordAudio.wav");
             byte [] mybytearray  = new byte [(int)myFile.length()];
             fis = new FileInputStream(myFile);
             bis = new BufferedInputStream(fis);
             bis.read(mybytearray,0,mybytearray.length);
           
             System.out.println("Sending " + "(" + mybytearray.length + " bytes)");
             os.write(mybytearray,0,mybytearray.length);
             os.flush();
             System.out.println("Done.");
           }
           finally {
             if (bis != null) bis.close();
          //   if (os != null) os.close();
           //  if (sock!=null) sock.close();
           }
         }
       }
       catch(Exception e)
       {
    	   e.printStackTrace();
    	   System.exit(0);
       }
        }
   
    
    public static void main(String args[])
    {
        new Voicecha().start2();
    }
    AudioFormat getAudioFormat()
    {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
    
    
    void start()
    {
    	
        try {
        	 wavFile = new File("E:/Test/RecordAudio.wav");
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing
 
            System.out.println("Start capturing...");
 
            AudioInputStream ais = new AudioInputStream(line);
            
            System.out.println("Start recording...");
 
            // start recording
            AudioSystem.write(ais, fileType, wavFile);
            System.out.println("finish");
           
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void start1()
    {
        try
        {
        while(true)
        {
        	new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					start();
				}
			}).start();
            
            Thread.sleep(1500);
            if(line==null)
            {
            	
            }
            else
            {	
            line.stop();
            line.close();
           this.test();
            }
            System.out.println("object sended");
        }
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }
 
    public void start2()
    {
        try
        {
            this.ss=new ServerSocket(7777);
            s=ss.accept();
            os = s.getOutputStream();
            System.out.println("connected");
            
            this.start1();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
        
    }
}