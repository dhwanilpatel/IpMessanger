package FinalMessenger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

public class Soundsender
{

		File wavFile = new File("E:/Test/RecordAudio.wav");
		AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
		TargetDataLine line;
		ServerSocket s;
		Socket s1;
		ObjectInputStream oips;
		OutputStream oops;
		AudioFormat format;
	public static void main(String args[]){
		Soundsender r=new Soundsender();
		r.createRecorder(); 
		r.start1();
		
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
        	  s=new ServerSocket(7770);
			s1=s.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          try {
			//oips=new ObjectInputStream(s1.getInputStream());
			oops=s1.getOutputStream();
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
			AudioSystem.write(ais, fileType, wavFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//         try {
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
	
	public void send()
	{
		int[] test;
		int a,i=0;
		FileInputStream fis = null;
	       BufferedInputStream bis = null;
		try {
			
//			FileReader f=new FileReader(wavFile);
//			test=new int[(int)wavFile.length()];
//			while((a=f.read())!=-1)
//			test[i++]=a;
//			test[i]=-1;
//			
//			oops.writeObject(test);
//			oops.flush();
//			System.out.println("file sended");
			
			byte [] mybytearray  = new byte [(int)wavFile.length()];
            fis = new FileInputStream(wavFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
          
            System.out.println("Sending " + "(" + mybytearray.length + " bytes)");
            oops.write(mybytearray,0,mybytearray.length);
            oops.flush();
            System.out.println("Done.");
                if (bis != null) bis.close();
             
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopRecording()
	{
		line.stop();
		line.close();
	}
	
}
