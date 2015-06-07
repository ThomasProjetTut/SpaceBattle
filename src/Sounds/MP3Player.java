package Sounds;

import javazoom.jl.player.*;
import java.io.*;
import java.util.Random;

public class MP3Player {

    private Player player;
    private InputStream is;

    /**
     * Creates a new instance of MP3Player
     */
    public MP3Player(String filename) {
        try {
            // Create an InputStream to the file
            is = new FileInputStream(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            player = new Player(is);
            PlayerThread pt = new PlayerThread();
            pt.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class PlayerThread extends Thread {
        public void run() {
            try {
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

}