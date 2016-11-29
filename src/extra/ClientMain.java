package extra;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClientMain {

	public static void main(String[] args) {
		//for all clients
		
		new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(Client.class);
            }
        }.start();
        
		new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(Client.class);
            }
        }.start();
		
		
	}


}
