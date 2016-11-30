package assignment7;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatClient {
	private JTextArea incoming;
	private JTextField outgoing;
	private BufferedReader reader;
	private PrintWriter writer;
	
	private JTextField nameset;
	private String name=""; 
	private boolean group=false;
	private static int numchatters=1;

	public void run() throws Exception {
		initView();
		setUpNetworking();
		
	}

	private void initView() {
		JFrame frame =new JFrame("Welcome to Messenger!");
		JPanel firstPanel = new JPanel();
		JLabel label=new JLabel("Username: ");
		nameset = new JTextField(10);
		JButton sendButton1 = new JButton("Login");
		
		firstPanel.add(label);
		firstPanel.add(nameset);
		firstPanel.add(sendButton1);
		
		frame.getContentPane().add(BorderLayout.CENTER, firstPanel);
		frame.setSize(500, 70);
		frame.setVisible(true);
		sendButton1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				openChatbox(frame, firstPanel);		
			}
		});
	}
	private void openChatbox(JFrame frame, JPanel firstPanel){
		name=nameset.getText();
		//JFrame frame =new JFrame("Ludicrously Simple Chat Client");
		frame.remove(firstPanel);
		frame.setTitle(name +"'s Chatbox");
		JPanel mainPanel = new JPanel();
		incoming = new JTextArea(15, 50);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		outgoing = new JTextField(20);
		JButton sendButton = new JButton("Send");
		JButton leave=new JButton("Leave");

	
		leave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				writer.println("\r"+ name+" left the message");
				writer.flush();
				frame.dispose();
			}
		});
		sendButton.addActionListener(new SendButtonListener());
		
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		mainPanel.add(leave);
		//leave.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//leave.setBackground(Color.BLUE);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(650, 400);
		frame.setVisible(true);	
		

	}

	private void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		Socket sock = new Socket("127.0.0.1", 4242);
		InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
		reader = new BufferedReader(streamReader);
		writer = new PrintWriter(sock.getOutputStream());
		System.out.println("networking established");
		Thread readerThread = new Thread(new IncomingReader());
		Thread readerThread2 = new Thread(new IncomingReader());
		readerThread.start();
		readerThread2.start();
	}

	class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			outgoing.setAlignmentX(Component.RIGHT_ALIGNMENT);
			writer.println(name+": "+ outgoing.getText());
			writer.flush();
			//outgoing.setAlignmentX(Component.RIGHT_ALIGNMENT);
			outgoing.setText("");
			outgoing.requestFocus();
			
		}
	}
	
	/*class Leave implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			writer.println("\r"+ name+" left the message");
			writer.flush();
			
			
		}
		
	}*/



	public static void main(String[] args) {
		JFrame frame =new JFrame("Welcome to Messenger!");
		JPanel firstPanel = new JPanel();
		JLabel label=new JLabel("Choose:");
		JButton groupchat = new JButton("Start Group Message");
		JButton direct = new JButton("Start Direct Message");
		
		firstPanel.add(label);
		firstPanel.add(groupchat);
		firstPanel.add(direct);
		
		frame.getContentPane().add(BorderLayout.CENTER, firstPanel);
		frame.setSize(500, 70);
		frame.setVisible(true);
		
		groupchat.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				frame.remove(firstPanel);
				JPanel panel2=new JPanel();
				JLabel label= new JLabel("How many friends in message?");
				JTextField tf=new JTextField(10);
				JButton b=new JButton("Create");
				
				panel2.add(label);
				panel2.add(tf);
				panel2.add(b);
				frame.getContentPane().add(BorderLayout.CENTER, panel2);
				frame.setVisible(true);				
				System.out.println("here");
				b.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ev){
						numchatters= Integer.parseInt(tf.getText());
						frame.dispose();
						for (int i=0; i<numchatters; i++){
							try {
								new ChatClient().run();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});	
			}
		});
		direct.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				numchatters=2;
				frame.dispose();
				for (int i=0; i<numchatters; i++){
					try {
						new ChatClient().run();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}			
		});
		
		
	

	}
	

	
	class IncomingReader implements Runnable {
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					
						incoming.append(message + "\n");
						incoming.setSelectedTextColor(Color.RED);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
