package model;

import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;
import javazoom.jl.player.Player;

public class SoundPlayer{
	
	//Oggetto dell'API javazoom
	private Player musicPlayer;
	
	//Costruttore
    public SoundPlayer(){
    	//vuoto
    }
    
    //Avvia un effetto sonoro
    public void playSoundEffect(String audioFilePath) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Clip audioClip = null;
                AudioInputStream ais = null;

                try {
                    ais = AudioSystem.getAudioInputStream(getClass().getResource("/" + audioFilePath));
                    AudioFormat format = ais.getFormat();
                    int bufferSize = (int) ais.getFrameLength() * format.getFrameSize();
                    DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, format, bufferSize);

                    if (!AudioSystem.isLineSupported(dataLineInfo))
                        throw new IOException("Error: the AudioSystem does not support the specified DataLine.Info object");

                    try {
                        audioClip = (Clip) AudioSystem.getLine(dataLineInfo);
                        audioClip.open(ais);
                        audioClip.setFramePosition(audioClip.getFrameLength());
                        audioClip.loop(1);
                    }
                    catch (LineUnavailableException lue) {
                        throw new IOException("Error: a LineUnavailableException exception was thrown");
                    }

                } catch (UnsupportedAudioFileException uafe) {
                    uafe.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } finally {
                    if (ais != null)
                        try {
                            ais.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        });
    }
    
    //Avvia la musica
    public void playMusic(String audioFilePath) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new Thread(() -> {
                    try {
                        InputStream inputStream = getClass().getResourceAsStream("/"+audioFilePath);
                        musicPlayer = new Player(inputStream);
                        musicPlayer.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });      
    }
    
    //Ferma la musica
    public void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.close();
            musicPlayer = null;
        }
    }

}
