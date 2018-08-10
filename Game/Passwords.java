import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.beans.*;
import java.io.*;


public class Passwords extends JDialog{

  static TreeMap<String, String> userInfo = new TreeMap<String, String>();
  static TreeMap<String, String> userCheck = new TreeMap<String, String>();
  static JPasswordField password = new JPasswordField();
  static String nameS = "";
  static boolean match = false;
  static JFrame window = new JFrame();


  public static void userInformation() throws FileNotFoundException{

    JPanel content = new JPanel();
    JButton setInfo = new JButton("Create Player Account");

    setInfo.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event){
          JTextField name = new JTextField();
          JPasswordField password = new JPasswordField();
          JComponent[] inputs = new JComponent[] {
            new JLabel("Name:"),
            name,
            new JLabel("Password:"),
            password
          };
          int result = JOptionPane.showConfirmDialog(null, inputs, "Sign up", JOptionPane.PLAIN_MESSAGE);
          if (result == JOptionPane.OK_OPTION) {
            try {
              FileWriter file = new FileWriter("Results.txt",true);
              String myPass = String.valueOf(password.getPassword());
              userInfo.put(name.getText(), myPass);
              nameS = name.getText();
              file.write(name.getText()+"  "+myPass +"\n");
              file.close();
              name.setText(" ");
              match = true;
            } catch(IOException e) {}

          }
        }
      });


    JButton logIn = new JButton("Log in");
    logIn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event){
          loadPassword();
          JTextField name = new JTextField();
          JPasswordField password = new JPasswordField();
          JComponent[] inputs = new JComponent[] {
            new JLabel("Name:"),
            name,
            new JLabel("Password:"),
            password
          };
          int result = JOptionPane.showConfirmDialog(null, inputs, "Log in", JOptionPane.PLAIN_MESSAGE);
          if (result == JOptionPane.OK_OPTION){
            String myPass= String.valueOf(password.getPassword());
            if (userCheck.containsKey(name.getText()) && userCheck.get(name.getText()).equals(myPass)){
              match = true;
              nameS = name.getText();
            } else {
            Component frame = new JFrame();
            JOptionPane.showMessageDialog(frame , "User name not exist or password not correct!" );
            }
          }
        }
      });


    content.add(setInfo);
    content.add(logIn);

    window = new JFrame("User Log In");
    window.setContentPane(content);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocation(600,450);
    window.pack();
    window.setVisible(true);

  }

  public static boolean getMatch(){
    System.out.print("");
    return match;
  }

  public static void loadPassword(){

    try{
      File password = new File("Results.txt");
      Scanner scanner = new Scanner(password);
      while (scanner.hasNextLine()){
        String[] words = scanner.nextLine().split(" +");
        userCheck.put(words[0],words[1]);
      }
    } catch(Exception e) {
      System.out.println("Exception:  "+e);
    }
  }

}
