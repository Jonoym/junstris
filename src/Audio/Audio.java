package Audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Audio {

    private File backgroundMusic;
    private AudioInputStream backgroundAIS;
    private Clip backgroundClip;

    private File placeNote;
    private AudioInputStream placeAIS;
    private Clip placeClip;

    public Audio(){
        try {
            this.placeNote = new File("src/AudioClips/Test3.wav");
        } catch (Exception e) { }
    }

    public void placePiece() {
        try {
            this.placeAIS = AudioSystem.getAudioInputStream(placeNote);
            this.placeClip = AudioSystem.getClip();

            placeClip.open(placeAIS);
            placeClip.start();
        } catch (Exception e) {
            System.out.println("Sound Problems");
        }
    }
}
