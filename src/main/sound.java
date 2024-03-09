package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    FloatControl fc;

    int volumeScale = 3;

    float volume;

    public sound()
    {
        soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
        try {
            if(soundURL[0] == null){
                throw new NoSoundException("Nu este acest sunet");
            }
        } catch (NoSoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFile(int i)
    {
        try
        {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[0]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void play()
    {
        clip.start();
    }

    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop()
    {
        clip.stop();
    }

    public void checkVolume(){

        switch (volumeScale) {
            case 0 -> {
                volume = -80f;
            }
            case 1 -> {
                volume = -20f;
            }
            case 2 -> {
                volume = -12f;
            }
            case 3 -> {
                volume = -5f;
            }
            case 4 -> {
                volume = 1f;
            }
            case 5 -> {
                volume = 6f;
            }
        }
        fc.setValue(volume);
    }
}
