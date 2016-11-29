package extra;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Observable;

//import chat_javafx_liang.MultiThreadServer.HandleAClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ServerMain extends Observable{
	
		public static void main(String[] args) {
			new Thread() {
	            @Override
	            public void run() {
	                javafx.application.Application.launch(Server.class);
	            }
	        }.start();
		}
}
