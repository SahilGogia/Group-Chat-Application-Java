package GroupChat;

import java.io.*;
import java.net.*;
import java.util.*;


public class clientThread extends Thread {
    private Socket socket;
    private Server server;
    protected PrintWriter writer;
    private String userName;
		private String serverMessage;
		private String name;
	  private String message;
    private String quitmessage;
		private String welcomemessage;


    public clientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
					InputStream input = socket.getInputStream();
				  BufferedReader reader = new BufferedReader(new InputStreamReader(input));
					OutputStream output = socket.getOutputStream();
				  writer = new PrintWriter(output, true);

						writer.println("Connected users: " + server.getUsernames());

            userName = reader.readLine();
						server.setUname(userName);
            server.addnametolist();

						name = userName;
            message = " has joined the chat!";
						welcomemessage = name.concat(message);
            server.broadcast(welcomemessage, this);
						String clientMessage="";
						while(true){
							clientMessage = reader.readLine();
							if (clientMessage.equals("quit chat")){
								server.removeUser(userName, this);
		            socket.close();
		            name = userName;
		            message = " exits the chat";
								quitmessage = name.concat(message);
		            server.broadcast(quitmessage, this);
								break;
							}
							else if (clientMessage.contains("launch bkp -txt")) {
							FileWriter myWriter = new FileWriter("chatbackup.txt");
							for(String str: server.chatbackup) {
              myWriter.write(str + System.lineSeparator());
                }
		          myWriter.close();
							}
							else if (clientMessage.contains("kick")) {
								String[] kick = clientMessage.split(" ");

								clientThread temp = server.getKickusr(kick[1]);
								server.kickuser(kick[1]);
								temp.socket.close();
							}
							else if (clientMessage.equals("active -u")) {
								ArrayList<String> fusernames = server.getUsernames();
								String temp = server.usernametostring(fusernames);
								server.printactiveusers(fusernames,this);
							}
							else if (clientMessage.contains("//private")) {
                 String[] priv = clientMessage.split(" ");
								 server.privbroadcast("private chat request",this, priv[1]);
							}else{

                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage, this);
              }
            }
						try{
           this.sleep(1000);
				 }catch(InterruptedException e){

				 }
        } catch (IOException ex) {
            System.out.println("Server Error");
        }
    }

}
