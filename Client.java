package GroupChat;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client {
	private String host = "localhost";
	private int port=8989;
	private String uname;
	protected String username ="";
	private int initalport;
	private String clientmessage;
	protected Socket sock;
	public void execute() {

		try {
			Socket socket = new Socket(host, port);
			System.out.println("Connected to the chat server");
			new ReadThread(socket, this).start();
			new WriteThread(socket, this).start();
		} catch (IOException ex) {
			System.out.println("Error getting input stream");
		}
	}
	public static void main(String[] args) {
		Client client = new Client();
		client.execute();
	}
}


class ReadThread extends Thread {  //Reads from the server
	private BufferedReader reader;
	private Socket socket;
	private Client client;
	private String response;

	public ReadThread(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;
		try {
			InputStream input = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException ex) {
			System.out.println("Error getting input stream");
		}
	}
	public void run() {
	while (true) {
			try {
			  response = reader.readLine();
				System.out.print("\n");
				System.out.println(response);
				if (client.username != null) {
					System.out.print("[" + client.username + "]: ");
				}else{
					System.out.println("Please enter a valid username");
				}
			} catch (IOException ex) {
				System.out.println("Error reading from server");
				break;
			}
		}
	}
}



class WriteThread extends Thread {  //writes to the server
	private PrintWriter writer;
	private Socket socket;
	private Client client;
  private String userName="";
	private String text="";
	public WriteThread(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;
		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
		} catch (IOException ex) {
			System.out.println("Error getting output stream");
		}
	}

	public void run() {
		Console console = System.console();
	  userName = console.readLine("\nEnter your name: ");
		client.username=userName;
		writer.println(userName);
		while(true) {
			text = console.readLine("[" + userName + "]: ");
			if(text.equals("bye")){
				break;
			}
			else{
			writer.println(text);
		  }
		}
		try{
			socket.close();
		}catch(IOException ex){
			System.out.println("Error in closing the socket");
		}

	}
}
