package Project.Client.Menus;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class MusicManager extends Thread{
    private Media music;
    private MediaPlayer mPlayer;
    private String songName;
    private  static  MusicManager musicManager;
    private MusicManager(){}

    public static MusicManager getInstance(){
        if(musicManager==null)
            musicManager=new MusicManager();
        return musicManager;
    }


    public void playSound(String action){
    /*    Media sound=null;
        MediaPlayer mediaPlayer=null;
        if(action.equalsIgnoreCase("Button")){
            File file=new File("src/main/resources/Sounds/ButtonSound.mp3");
            try {
                sound=new Media(file.toURI().toURL().toExternalForm());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if(action.equalsIgnoreCase("Error")){
            File file=new File("src/main/resources/Sounds/error.mp3");
            try {
                sound=new Media(file.toURI().toURL().toExternalForm());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if(action.equalsIgnoreCase("notify")){
            File file=new File("src/main/resources/Sounds/notify.wav");
            try {
                sound=new Media(file.toURI().toURL().toExternalForm());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        mediaPlayer=new MediaPlayer(sound);
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.play(); */
    }
//    @Override
//    public void run() {
//        String path="src/main/resources/Sounds/"+songName;
//        try {
//            music=new Media(new File(path).toURI().toURL().toExternalForm());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        mPlayer=new MediaPlayer(music);
//        mPlayer.setAutoPlay(true);
//        mPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//        mPlayer.setVolume(0.2);
//        mPlayer.play();
//    }

    public void setSongName(String songName){
     /*   this.songName=songName;
        if(mPlayer!=null) mPlayer.pause();
        String path="src/main/resources/Sounds/"+songName;
        try {
            music=new Media(new File(path).toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mPlayer=new MediaPlayer(music);
       mPlayer.setAutoPlay(true);
        mPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mPlayer.setVolume(0.2);
       mPlayer.play(); */
    }





}
