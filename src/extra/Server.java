package extra;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application{

	/*public static void main(String[] args){
	Server server=new Server();
	
	new Thread(new Runnable(){
		@Override
		public void run(){
			server.runme();
		}
	}).start();	
} */

// Text area for displaying contents 
	private TextArea ta = new TextArea(); 

	// Number a client 
	private int clientNo = 0; 

	@Override // Override the start method in the Application class 
	public void start(Stage primaryStage) { 
		// Create a scene and place it in the stage 
		Scene scene = new Scene(new ScrollPane(ta), 450, 200); 
		primaryStage.setTitle("MultiThreadServer"); // Set the stage title 
		primaryStage.setScene(scene); // Place the scene in the stage 
		primaryStage.show(); // Display the stage 

		new Thread( () -> { 
			try {  // Create a server socket 
				ServerSocket serverSocket = new ServerSocket(8000); 
				ta.appendText("MultiThreadServer started at " 
						+ new Date() + '\n'); 


				while (true) { 
					// Listen for a new connection request 
					Socket socket = serverSocket.accept(); 

					// Increment clientNo 
					clientNo++; 

					Platform.runLater( () -> { 
						// Display the client number 
						ta.appendText("Starting thread for client " + clientNo +
								" at " + new Date() + '\n'); 

						// Find the client's host name, and IP address 
						InetAddress inetAddress = socket.getInetAddress();
						ta.appendText("Client " + clientNo + "'s host name is "
								+ inetAddress.getHostName() + "\n");
						ta.appendText("Client " + clientNo + "'s IP Address is " 
								+ inetAddress.getHostAddress() + "\n");	}); 


					// Create and start a new thread for the connection
					new Thread(new HandleAClient(socket)).start();
				} 
			} 
			catch(IOException ex) { 
				System.err.println(ex);
			}
		}).start();
	}


	// Define the thread class for handling
	class HandleAClient implements Runnable {
		private Socket socket; // A connected socket
		/** Construct a thread */ 
		public HandleAClient(Socket socket) { 
			this.socket = socket;
		}
		/** Run a thread */
		public void run() { 
			try {
				// Create data input and output streams
				DataInputStream in = new DataInputStream( socket.getInputStream());
				DataOutputStream out = new DataOutputStream( socket.getOutputStream());
				BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter outputToClient = new PrintWriter( socket.getOutputStream());
				while (true) {
					String msg1 = inputFromClient.readLine();
					ta.appendText("Server received message: "+ msg1+"\n");
					
					//do a continuous thing of clients?
					//how to tag name automatically?
					//or append name automatically when sending from client's side
					//String msg2=
					
				}
				// Continuously serve the client
		/*		while (true) { 
					// Receive radius from the client 
					String txt = inputFromClient.readLine();
					ta.appendText("Server received message: "+ txt);
					// Send area back to the client    // outputToClient.writeDouble(area);
					outputToClient.write("received");
					Platform.runLater(() -> { 
						ta.appendText("confirmed"+ '\n'); 
						
					});
				} */
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
