import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class ui {
public static void main(String[] args) {

JFrame f=new JFrame();//creating instance of JFrame

JTextArea area=new JTextArea();  //messages are displayed here
area.setBounds(10,10, 780,400);
area.setEditable(false);
f.add(area);

JTextArea textArea=new JTextArea(); //messages are typed here
textArea.setBounds(10,450,780,110);
f.add(textArea);


JButton b=new JButton("Send");// send button to send the messages
b.setBounds(840,450,95,110);

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
f.setSize(1030,700);
f.setLayout(null);//using no layout managers
f.setVisible(true);//making the frame visible
}
}
