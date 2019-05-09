/*==============================================
 * Project: City-Saver
 * Author: Khair Ahammed
 * Date: 3/5/18
==============================================*/

package CitySaver;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio implements Runnable{

    // size of the byte buffer used to read/write the audio stream
    private static final int BUFFER_SIZE = 4096;		
    String audioFilePath;		

    public Audio(String audioFilePath) {		
        this.audioFilePath = audioFilePath;		
    }		

    @Override
    public void run() {
        File audioFile = new File(audioFilePath);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

            audioLine.open(format);

            audioLine.start();

            byte[] bytesBuffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                audioLine.write(bytesBuffer, 0, bytesRead);
            }

            audioLine.drain();
            audioLine.close();
            audioStream.close();


        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }

    public void play() {
        Audio player = new Audio(audioFilePath);
        Thread a = new Thread(player);
        a.start();
    }
}