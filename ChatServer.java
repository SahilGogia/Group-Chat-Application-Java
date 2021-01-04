package server;

import java.io.*;
import java.net.*;
import java.util.*;


public class ChatServer extends Exception{
    private String uname="";
    private ArrayList<UserThread> users = new ArrayList<UserThread>();
    private ArrayList<String> usernames = new ArrayList<String>();
    private int port = 8989;
    private String chatmessage = "";
    private String privname ="";
    private String kickname ="";
    private UserThread kickusr;
    protected ArrayList<String> chatbackup = new ArrayList<String>();

	public void execute() {
		try {
      ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Chat Server port -- " + port);
			for(;;) {
				Socket socket = serverSocket.accept();
        System.out.println("Connecting user...");
				System.out.println("User connected");
				UserThread newUser = new UserThread(socket, this);
			  users.add(newUser);
				newUser.start();

			}

		}catch (Exception ex) {
            System.out.println("Error in the server");
        }
	}
  void setKickname(String name){
       this.kickname = name;
  }
  UserThread getKickusr(String user){
    int userkick = usernames.indexOf(user);
    UserThread aUser2 = users.get(userkick);
    return aUser2;
  }
  void kickuser(String user){
    int userkick = usernames.indexOf(user);
    UserThread aUser2 = users.get(userkick);
    removeUser(user, aUser2);

  }
  void setPrivname(String name){
       this.privname = name;
  }
  void privbroadcast(String message,UserThread sender, String receiver){
    int aUser1= usernames.indexOf(receiver);
    UserThread aUser2;
    for (int i = 0; i < users.size(); i++){
        aUser2 = users.get(i);

            if(aUser2 == users.get(aUser1)){
                System.out.println("loop");
                users.get(aUser1).writer.println(message);
        }


    }

  }

    int broadcast(String message,UserThread excludeUser){
        UserThread aUser1;
        UserThread aUser2 = excludeUser;
        for (int i = 0; i < users.size(); i++){
            aUser1 = users.get(i);

                if(aUser1 == aUser2){
                    continue;
            }

              chatbackup.add(message);
            aUser1.writer.println(message);

        }

        return 1;
    }

  void printactiveusers(ArrayList<String> f,UserThread usr){
    usr.writer.println(f);
  }

    void setUname(String name){
        this.uname = name;
    }

    void addnametolist(){
        String tempname = "";
        tempname = this.uname;
        usernames.add(tempname);

    }

	void removeUser(String userName, UserThread aUser) {
          usernames.remove(userName);
          users.remove(aUser);
          System.out.println( userName + " quits the chat room");

	}

    ArrayList<String> getUsernames() {
		return this.usernames;
	}

  void showactiveusers(){
    System.out.println(usernames);
  }

  String usernametostring(ArrayList<String> fusernames){
    String retnames="";
    for (int i = 0; i < fusernames.size(); i++){
         retnames +=fusernames.get(i);
    }
    return retnames;
  }

    public static void main(String[] args) {

        ChatServer server = new ChatServer();
        server.execute();
    }


}
