import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

import FinalMessenger.Soundsender;

public class SimpleFileServer {

  public final static int SOCKET_PORT = 13267;  // you may change this
  public final static String FILE_TO_SEND = "c:/temp/source.pdf";  // you may change this
	 
	AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	TargetDataLine line;
//	ServerSocket s;
//	Socket s1;
	
	AudioFormat format;
	
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
    static File myFile = new File("E:/Test/RecordAudio.wav");;
  public static void main (String [] args ) throws IOException {
	  
	  Soundsender r=new Soundsender();
		r.createRecorder(); 
		r.start1();
	
   
  }
  public void send()
  {
	 
	      while (true) {
	        System.out.println("Waiting...");
	        try {
	          
	        	
	          // send file
	         
	          byte [] mybytearray  = new byte [(int)myFile.length()];
	          fis = new FileInputStream(myFile);
	          bis = new BufferedInputStream(fis);
	          bis.read(mybytearray,0,mybytearray.length);
	         
	          System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
	          os.write(mybytearray,0,mybytearray.length);
	          os.flush();
	          System.out.println("Done.");
	          if (bis != null) bis.close();
	          if (os != null) os.close();
	          if (sock!=null) sock.close();
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
	      }
	    
	    
  }
  public void createRecorder()
	{
		  format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        // checks if system supports the data line
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line not supported");
            System.exit(0);
        }
        try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
        	 servsock = new ServerSocket(SOCKET_PORT);
        	 sock = servsock.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			//oips=new ObjectInputStream(s1.getInputStream());
        	 os = sock.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}
	
	AudioFormat getAudioFormat() {
      float sampleRate = 16000;
      int sampleSizeInBits = 8;
      int channels = 2;
      boolean signed = true;
      boolean bigEndian = true;
      AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                           channels, signed, bigEndian);
      return format;
  }
	
	public void startRecording()
	{
		try {
			line.open(format);
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 line.start();   // start capturing
		 
     
       AudioInputStream ais = new AudioInputStream(line);

       System.out.println( "start recording");
       try {
			AudioSystem.write(ais, fileType, myFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//       try {
//			Thread.sleep(50);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
       stopRecording();

	}
//	public void start1()
//	{
//		t.start();
//	}
	
	public void start1()
	{
		while(true)
		{
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					startRecording();
				}
			}).start();
			System.out.println("in run");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			send();
			
		}
	}
	
	public void stopRecording()
	{
		line.stop();
		line.close();
	}
	
	
}