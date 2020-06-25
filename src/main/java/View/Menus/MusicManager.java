package View.Menus;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;

public class MusicManager extends Thread{
    private  static  MusicManager musicManager;
    private MusicManager(){}

    public static MusicManager getInstance(){
        if(musicManager==null)
            musicManager=new MusicManager();
        return musicManager;
    }


    public void playSound(String action){
        Media sound=null;
        MediaPlayer mediaPlayer=null;
        if(action.equalsIgnoreCase("Button")){
            File file=new File("src/main/resources/Sounds/ButtonSound.mp3");
            try {
                sound=new Media(file.toURI().toURL().toExternalForm());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        mediaPlayer=new MediaPlayer(sound);
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.play();

    }


    @Override
    public void run() {

    }





}
