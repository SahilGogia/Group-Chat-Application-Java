import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

class ChatClient {
	private String host = "localhost";
	private int port=8989;
	private String uname;
	protected String username ="";
	private int initalport;
	private String clientmessage;
	protected Socket sock;

	public ChatClient() {
		this.execute();
	}

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
}
class ReadThread extends Thread {
	private BufferedReader reader;
	private Socket socket;
	private ChatClient client;
	private String response;

	public ReadThread(Socket socket, ChatClient client) {
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

class WriteThread extends Thread {
	private PrintWriter writer;
	private Socket socket;
	private ChatClient client;
  private String userName="";
	private String text="";
	public WriteThread(Socket socket, ChatClient client) {
		this.socket = socket;
		this.client = client;
		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
		} catch (IOException ex) {
			System.out.println("Error getting output stream");
		}
	}
	public void run(String m) {
		client.username=userName;
		//System.out.println(client.username);
		writer.println(userName);
		writer.println(m);
		try{
			socket.close();
		}catch(IOException ex){
			System.out.println("Error in closing the socket");
		}

	}

	public void sendMessage(String m) {
		client.username=userName;
		//System.out.println(client.username);
		writer.println(userName);
		writer.println(m);
		try{
			socket.close();
		}catch(IOException ex){
			System.out.println("Error in closing the socket");
		}

	}
}

public class UIF {
	private ChatClient cc;
	private WriteThread wt;

	public UIF() {
		try{
		Socket socket = new Socket("localhost",8989);
		cc = new ChatClient();
		wt = new WriteThread(socket,cc);
	}catch(Exception e){

	}
}
public void gui(){

	JFrame f=new JFrame();//creating instance of JFrame
	  JTextArea area=new JTextArea();
	        area.setBounds(10,10, 780,400);
	        area.setEditable(false);
	        f.add(area);
	 JTextArea textArea=new JTextArea();
	         textArea.setBounds(10,450,780,110);
	  JButton b=new JButton("Send");
	  b.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			String str = textArea.getText();
			wt.run(str);
		}
		});
	    b.setBounds(840,450,95,110);

	  f.add(textArea);
	   f.add(b);

	   textArea.setLineWrap(true);
	        textArea.setWrapStyleWord(true);
	JScrollPane scrolltxt1=new JScrollPane(textArea);
	        scrolltxt1.setBounds(10,450,780,110);

	     f.add(scrolltxt1);

	     JScrollPane scrolltxt2=new JScrollPane(area);
	             scrolltxt2.setBounds(10,10, 780,400);

	          f.add(scrolltxt2);

	          JTextArea active=new JTextArea();
	                active.setBounds(800,10, 200,400);
	                active.setText("  Currently active users");
	                active.setEditable(false);
	                f.add(active);
	                JScrollPane scrolltxt3=new JScrollPane(active);
	                        scrolltxt3.setBounds(800,10, 200,400);

	                     f.add(scrolltxt3);
	Image icon = Toolkit.getDefaultToolkit().getImage("snoop.jpg");
	f.setIconImage(icon);
	f.setTitle("OG chat");
	f.setResizable(false);
	f.setSize(1030,700);//400 width and 500 height
	f.setLayout(null);//using no layout managers
	f.setVisible(true);//making the frame visible
}




public static void main(String[] args) {

 UIF ui = new UIF();
 ui.gui();

}
}
