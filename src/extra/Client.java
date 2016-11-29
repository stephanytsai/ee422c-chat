package extra;


import java.io.*;
import java.net.*;
import java.util.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application implements Observer{
	int port = 8000;
	String host = "localhost";
	DataInputStream in; //DEBUG, may affect server printing stuff?
	DataOutputStream out;
	Socket socket;
	double aNumber = 5;
	String name="Bob"; //user's name DEBUG 
	
	PrintWriter out2;
	BufferedReader in2;		

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		// Panel p to hold the label and text field 
		BorderPane paneForTextField = new BorderPane(); 
		paneForTextField.setPadding(new Insets(5, 5, 5, 5)); 
		paneForTextField.setStyle("-fx-border-color: green"); 
		paneForTextField.setLeft(new Label("Text: ")); 
		

		TextField tf = new TextField(); 
		tf.setAlignment(Pos.BOTTOM_RIGHT); 
		paneForTextField.setCenter(tf); 

		BorderPane mainPane = new BorderPane(); 
		// Text area to display contents 
		TextArea ta = new TextArea(); 
		mainPane.setCenter(new ScrollPane(ta)); 
		mainPane.setTop(paneForTextField); 

		// Create a scene and place it in the stage 
		Scene scene = new Scene(mainPane, 450, 200); 
		primaryStage.setTitle(name+" & Co Talking"); // Set the stage title 
		primaryStage.setScene(scene); // Place the scene in the stage 
		primaryStage.show(); // Display the stage 

		
	/*	tf.setOnAction(e -> { 
				// Get the radius from the text field 
				String txt = tf.getText().trim(); 
				if (txt != null){System.out.println("got text");} //DEBUG
				ta.appendText("me: " + txt + "\n");
				// Send the radius to the server 
				out2.println(txt); 
				out2.flush(); 
		}); 
		
		//try {
			// Define client socket, and initialize in and out streams.
			socket = new Socket(host, port);
			PrintWriter out2 = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in2 =new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String txt;
			while (true) {
				// ask user to enter text
				txt=tf.getText().trim();
				// send the double to the server
				out2.println(txt);
				out2.flush();
				// read the server's response, and print it out.
				System.out.println("response: "+in2.readLine());
			}
		/*} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}
	
	public static void main(String[] args){
		launch(args);
	}



}
