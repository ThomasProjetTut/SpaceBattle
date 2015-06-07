package Sounds;

import java.util.Random;

public class MusicPlayer extends Thread{
    MP3Player player;
    String[] musicBank;
    Random random;
    int count;
    int newCount;


    public MusicPlayer (){
        musicBank = new String[]{"music/TheNewWorld.mp3",
                "music/TheSummit.mp3",
                "music/UnderTheOceanCurrents.mp3",
                "music/IntoTheCaverns.mp3",
                "music/TheTundraWastes.mp3"};

        random = new Random();
        count = random.nextInt(musicBank.length);
        player = new MP3Player(musicBank[count]);
    }

    public void run(){
        player.play();

        while (true){

            if (player.getPlayer().isComplete()){
                newCount = random.nextInt(musicBank.length);

                while (count == newCount){
                    newCount = random.nextInt(musicBank.length);
                }

                count = newCount;

                player = new MP3Player(musicBank[count]);
            }

            player.play();
        }
    }
}
