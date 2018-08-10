import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

public class ClickGame extends JPanel {
    private static int result = 0;
    private static int scores;
    private static boolean playing = false;
    private static boolean menu = true;
    private static boolean end = false;
    private static int count = 30;
    private static JPanel outro;
    private static JPanel outro1;
    private static JPanel outro2;
    private static ClickGame drawingArea;
    private static ArrayList<FallingSquare> squares = new ArrayList<FallingSquare>();
    private static JLabel resultL = new JLabel();

    public static void main(String[] args) throws InterruptedException{
        try {
          Passwords.userInformation();
        } catch (FileNotFoundException e){}

        boolean off = true;
        while(off){
          if (Passwords.getMatch()==true) {
            Passwords.window.setVisible(false);
            try{runGame();} catch (InterruptedException e){}
            off = false;
          }
        }
    }

    public static void runGame() throws InterruptedException{

        drawingArea = new ClickGame();
        JFrame frame = new JFrame("Group 04 - Welcome To ClickGame");
        JPanel content = new JPanel(new BorderLayout());
        JPanel intro = new JPanel(new GridLayout(3,1));

        outro = new JPanel(new GridLayout(0,1));
        outro1 = new JPanel(new GridLayout(0,1));
        outro2 = new JPanel(new GridLayout(0,1));

        JButton startB = new JButton("<html><h2>START</h2></html>");
        JButton pauseB = new JButton("<html><h2>PAUSE</h2></html>");
        JButton resultB = new JButton("<html><h2>VIEW RANKING</h2></html>");
        JButton restartB = new JButton("<html><h2>RESTART</h2></html>");
        JButton musicB = new JButton("<html><h2>SET MUSIC</h2></html>");
        JLabel timeL = new JLabel("<html><h3>Time remaining: 30s</h3></html>");
        JLabel titleCard = new JLabel("<html><h2>Welcome to ClickGame</h2></html>");
        JLabel note = new JLabel("<html><h3>Click the squares to earn points!</h3></html>");
        titleCard.setHorizontalAlignment(JLabel.CENTER);
        note.setHorizontalAlignment(JLabel.CENTER);
        timeL.setHorizontalAlignment(JLabel.CENTER);
        drawingArea.setBackground(new Color(148,184,184));

        musicB.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            WavPlayerDemo t = new WavPlayerDemo("file:Medley1.wav");
            t.setVisible(true);
          }
        });

        startB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (drawingArea.menu) {
                    drawingArea.playing = true;
                    drawingArea.menu = false;
                    content.add(drawingArea, BorderLayout.CENTER);
                    content.validate();
                    content.repaint();
                }
                drawingArea.timer(timeL);
            }
        });

        restartB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              outro.removeAll();
              outro.add(outro1);
              outro.validate();
              drawingArea = new ClickGame();
              drawingArea.setBackground(new Color(148,184,184));
              count = 30;
              drawingArea.menu = true;
              drawingArea.end = false;
              if (drawingArea.menu) {
                  drawingArea.playing = true;
                  drawingArea.menu = false;
                  content.add(drawingArea, BorderLayout.CENTER);
                  content.revalidate();
                  content.repaint();
              }
              drawingArea.timer(timeL);

            }
        });


        pauseB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(drawingArea.playing){
                  pauseB.setText("<html><h2>RESUME</h2></html>");
                  drawingArea.playing = !drawingArea.playing;
                }else if(!drawingArea.playing){
                  pauseB.setText("<html><h2>PAUSE</h2></html>");
                  drawingArea.playing = !drawingArea.playing;
                }

            }
        });

        resultB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              Rank rankChart = new Rank();
              JPanel content = new JPanel();
              JScrollPane scrollpane = new JScrollPane(rankChart.table);
              content.add(scrollpane);

              JFrame window = new JFrame("Rank");
              window.setContentPane(content);
              //window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              window.setLocation(120,70);
              window.pack();
              window.setVisible(true);

            }
        });

        drawingArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int mx = evt.getX();  // x-coordinate where user clicked.
                int my = evt.getY();  // y-coordinate where user clicked.
                for (FallingSquare s: squares) {
                    int theX = s.getXCoordinate();
                    int theY = s.getYCoordinate();
                    if (((theX < mx) && (mx < theX + 30)) && ((theY < my) && (my < theY + 30))) {
                        drawingArea.result++;
                        System.out.println("hit");
                    }
                }
            }
        });

        intro.add(titleCard);
        intro.add(note);
        intro.add(timeL);
        outro1.add(pauseB);
        outro1.add(startB);
        outro2.add(resultB);
        outro2.add(restartB);
        outro2.add(musicB);
        outro2.add(resultL);
        outro.add(outro1);
        content.add(intro,BorderLayout.PAGE_START);
        content.add(outro,BorderLayout.PAGE_END);
        frame.setContentPane(content);
        frame.setSize(500, 450);
        frame.setLocation(300,100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

        while (true) {
            if (drawingArea.playing) {
                drawingArea.update();
                drawingArea.repaint();
                Thread.sleep(17);
            } else {
                Thread.sleep(17);
            }
        }
    }

    public ClickGame() {

        for (int i = 0; i < 15; i++) {
            FallingSquare s = new FallingSquare();
            squares.add(s);
        }
    }


    public void paintComponent(Graphics g) {
        //paints square objects to the screen
        try {
          for (FallingSquare squareArray : squares) {
            squareArray.paintComponent(g);
          }
        } catch (NullPointerException e){}
    }

    public void timer(JLabel timeL) {
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
        public void run() {
          timeL.setText("<html><h3>Time remaining: "+count+"s</h3></html>");
          if (count<=0 ) {
            timer.cancel();
            squares.clear();
            resultL.setText("Your score is "+drawingArea.result);
            playing = false;
            outro.removeAll();
            outro.add(outro2);
            outro.revalidate();
            try{
              FileWriter file = new FileWriter("rank.txt",true);
              file.write(Passwords.nameS+"  "+drawingArea.result+"\n");
              file.close();
            } catch (IOException e){}
            drawingArea.result=0;
          }
          if(drawingArea.playing){
            count--;
          }
         }
       }, 0L, 1000L);
    }

    public void update() {
        //calls the Square class update method on the square objects
        for (FallingSquare squareArray : squares) {
            squareArray.update();
        }
    }
}
