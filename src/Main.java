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


public class Main 
{
	private JFrame f;
	private JPanel user;
	private JPanel friend;
	private JTextArea text;
	private BufferedImage img;
	private ObjectOutputStream oops;
	private ObjectInputStream oips;
	public static void main(String args[])
	{
		new Main().start();
	}
	public void start()
	{	
		
		f=new JFrame();
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
		f.setSize(1000,700);
		
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
//		text.addKeyListener(new KeyListener() {
//			public void keyTyped(KeyEvent arg0) {
//				if(arg0.getExtendedKeyCode()==10)
//					send();
//			}
//			public void keyReleased(KeyEvent arg0) {}
//			public void keyPressed(KeyEvent arg0) {}
//		});
		
		Socket s=null;
		try{
		ServerSocket ss = new ServerSocket(7777);
		s=ss.accept();
		
		oops = new ObjectOutputStream(s.getOutputStream());
		oips = new ObjectInputStream(s.getInputStream());

		
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
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
		f.setSize(f.getSize().width==1001?1000:1001,f.getSize().height==701?700:701);
		f.repaint();
	}
}
