package Global;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    Clip super_idol_hardstyle;
    Clip metal_pipe;
    Clip sweden;
    Clip block;
    Clip moan;

    public Sound() {
        super_idol_hardstyle = openClip("/home/do/Music/super_idol_hardstyle.wav");
        metal_pipe = openClip("/home/do/Music/metal_pipe.wav");
        block = openClip("/home/do/Music/block_short.wav");
        moan = openClip("/home/do/Music/moan.wav");
        sweden = openClip("/home/do/Music/Sweden.wav");
        setVolume(moan, -10.0f);
        setVolume(metal_pipe, 6.0f);
        setVolume(block, 6.0f);
        setVolume(sweden, -17.0f);
        sweden.start();
    }

    private Clip openClip(String filepath) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filepath)));
            return clip;
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException err) {
            err.printStackTrace();
            System.exit(3);
            return null;
        }
    }

    private void setVolume(Clip clip, Float decible) {
        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(decible);
    }

    public void playMetalPipe() {
        metal_pipe.setFramePosition(0);
        metal_pipe.start();
    }

    public void playMoan() {
        moan.setFramePosition(0);
        moan.start();
        moan.drain();
    }

    public void playBlock() {
        block.setFramePosition(0);
        block.start();
        block.drain();
    }

    public void playSuperIdol() {
        super_idol_hardstyle.setFramePosition(0);
        super_idol_hardstyle.start();
    }
}

