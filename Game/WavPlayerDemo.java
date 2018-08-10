import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WavPlayerDemo extends JFrame {
    JButton btn = new JButton("Play Sound");
    JButton stp = new JButton("Stop Music");
    File wavFile;
    URL defaultSound;
    public static Clip clip;
    public static AudioInputStream audioInputStream;

    public WavPlayerDemo(String url) {
        try {
            defaultSound = new URL (url);
            JToolBar tb = new JToolBar("BGM");
            tb.add(btn);
            tb.add(stp);
            getContentPane().add(tb);
            setLocation(400, 300);
            pack();


            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    play();
                }
            });

            stp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stop();
                }
            });

            } catch (MalformedURLException ex) {
            Logger.getLogger(WavPlayerDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void play() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(defaultSound);

            try {
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.loop(20000);
                clip.start();

            } catch (LineUnavailableException e) {
            }

        } catch (UnsupportedAudioFileException | IOException e) {
        }
    }

    public void stop() {
        clip.stop();
    }
    public static void main(String args[]) {
       WavPlayerDemo t = new WavPlayerDemo("file:Medley1.wav");
       t.setVisible(true);

   }
}
