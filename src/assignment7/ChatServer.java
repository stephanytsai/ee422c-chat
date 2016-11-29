package assignment7;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChatServer extends Observable {
	private static JTextField out;
	private static JFrame frame;
	private static JPanel panel;
	private static JButton b;
	private static JLabel label;
//	private static int servernum; 
	private ArrayList userList=new ArrayList();
	private ArrayList passList=new ArrayList();
	
	public static void main(String[] args) {
		//login_users();
			int servernum=4242;
			//new window; each time you open a thing, can open a new chat room, direct or group message
			frame =new JFrame("Welcome to Messenger!");
			panel = new JPanel();
			label=new JLabel("Let's Chat!");
			b=new JButton("Start New Message");
			b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev){
					System.out.println("pushed");
					try {
						ChatClient.main(args);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
		
			BufferedImage myPicture;
			try {
				myPicture = ImageIO.read(ChatServer.class.getResource("Telegram_Messenger.png"));
				ImageIcon icon=new ImageIcon(myPicture);
				Image img1=icon.getImage();
				Image img2=img1.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
				icon=new ImageIcon(img2);
				JLabel la=new JLabel(icon);
				panel.add(la);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			}

			panel.add(label);
			panel.add(b);
		
			frame.getContentPane().add(BorderLayout.CENTER, panel);
			frame.setSize(300, 200);
			frame.setVisible(true);
			frame.setResizable(false);

			try {
				new ChatServer().setUpNetworking(servernum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //was surrounded by try catch
	}
	
	public static void login_users(){
		JFrame f= new JFrame("Login to Messenger");
		JPanel p= new JPanel();
		JButton b1=new JButton("New User");
		JButton b2=new JButton("Existing User");
		p.add(b1); p.add(b2);

		f.getContentPane().add(BorderLayout.CENTER, p);
		f.setSize(500, 70);
		f.setVisible(true);
		
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				JLabel l3=new JLabel("Create New User! \n Choose a username and password less than 30 charac"
						+ "ters long");
				p.add(l3);
				
			}
		});
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				JLabel l3=new JLabel("Log in");
				p.add(l3);
				f.setVisible(true);
				JLabel l1= new JLabel("Username: ");
				JLabel l2=new JLabel("Password: ");
				JTextField user=new JTextField(30);
				JTextField pass= new JTextField(30);
				p.add(l1);
				p.add(user);
				p.add(l2);
				p.add(pass);
			}
		});
			
	}

	

	private void setUpNetworking(int server_num) throws Exception {
		@SuppressWarnings("resource")
		ServerSocket serverSock = new ServerSocket(server_num);

		while (true) {
			Socket clientSocket = serverSock.accept();
			ClientObserver writer = new ClientObserver(clientSocket.getOutputStream());
			Thread t = new Thread(new ClientHandler(clientSocket));
			t.start();
			this.addObserver(writer);
			System.out.println("got a connection");
		}
	}
	class ClientHandler implements Runnable {
		private BufferedReader reader;

		public ClientHandler(Socket clientSocket) {
			Socket sock = clientSocket;
			try {
				reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("server read "+message);
					setChanged();
					notifyObservers(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
