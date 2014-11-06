package FinalMessenger;
import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Reciver 
{
	static JFrame f;
	private JPanel user;
	private JPanel friend;
	private JTextArea text;
	private BufferedImage img;
	private ObjectOutputStream oops;
	private ObjectInputStream oips;
	private static Client c;
	public static void main(String args[])
	{
		
		c=new Client();
		new Reciver().start();
	}
	public void start()
	{	
		
		f=new JFrame("client");
		user=new JPanel();
		text = new JTextArea("EnterMessage here");
		JScrollPane jspu = new JScrollPane(user);
		friend = new JPanel();
		JScrollPane jspf = new JScrollPane(friend);
		user.setLayout(new BoxLayout(user, BoxLayout.Y_AXIS));
		friend.setLayout(new BoxLayout(friend, BoxLayout.Y_AXIS));
		f.add(jspu,BorderLayout.EAST);
		f.add(jspf,BorderLayout.WEST);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setSize(900,600);
		
		JScrollPane jspt = new JScrollPane(text);
		f.add(jspt,BorderLayout.SOUTH);
		JButton b = new JButton("submit");
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// TODO Auto-generated method stub
				send();
				
			}
		});
		f.add(b,BorderLayout.NORTH);
		
		Socket s=null;
		try{
		//ServerSocket ss = new ServerSocket(7777);
		s=new Socket("127.0.0.1",7777);
		
		oops = new ObjectOutputStream(s.getOutputStream());
		oips = new ObjectInputStream(s.getInputStream());
		c.createcamera();
		
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		c.startRecording();
		f.setVisible(true);
		read();
	}
	private void send()
	{
		f.repaint();
		try {
			oops.writeObject(text.getText());
			addMessage(true,text.getText());
		} catch (IOException e) {
			
			e.printStackTrace();
			System.exit(0);
		}
		text.setText("");
	}
	
	private void read()
	{
		
		while(true)
		{
			f.repaint();
			try {
				addMessage(false,(String)oips.readObject());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void addMessage(boolean isUser,String msg)
	{
		System.out.println(isUser+"   "+msg);
		int maxLength=20;
		
		while(true)
		{
			if(msg.length()>maxLength)
			{
				if(isUser)
				{
					user.add(new JLabel(msg.substring(0, maxLength)));
					friend.add(new JLabel("\n"));
				}
				else
				{
					friend.add(new JLabel(msg.substring(0, maxLength)));
					user.add(new JLabel("\n"));
				}
				
				msg=msg.substring(maxLength);
			}
			else
			{
				if(isUser)
				{
					user.add(new JLabel(msg));
					friend.add(new JLabel("\n"));
				}
				else
				{
					friend.add(new JLabel(msg));
					user.add(new JLabel("\n"));
				}
				break;
			}
			
		}
		f.setSize(f.getSize().width==901?900:901,f.getSize().height==601?600:601);
		f.repaint();
	}
}
