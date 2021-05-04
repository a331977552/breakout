package org.game.game2d.sound;

import org.game.game2d.sound.filters.AbstractSoundFilter_2942625;

import java.io.*;
import javax.sound.sampled.*;

/**
 * added loop feature
 */
public class Sound_2942625 implements Runnable {

	private final boolean loop; //added loop feature.
	private AbstractSoundFilter_2942625 filter;
	String filename;	// The name of the file to play
	boolean finished;	// A flag showing that the thread has finished

	public Sound_2942625(String fname) {
		filename = fname;
		finished = false;
		loop = false;
	}

	public Sound_2942625(String fname, boolean loop, AbstractSoundFilter_2942625 filter) {
		this.filename = fname;
		this.loop = loop;
		this.filter = filter;
	}

	/**
	 * run will play the actual sound but you should not call it directly.
	 * You need to call the 'start' method of your sound object (inherited
	 * from Thread, you do not need to declare your own). 'run' will
	 * eventually be called by 'start' when it has been scheduled by
	 * the process scheduler.
	 */
	public void run() {
		try {
			do {

				File file = new File(filename);
				AudioInputStream stream = AudioSystem.getAudioInputStream(file);
				AudioFormat	format = stream.getFormat();

				DataLine.Info info = new DataLine.Info(Clip.class, format);
				Clip line = (Clip)AudioSystem.getLine(info);
				if(filter !=null ){
					SoundFilterStream_2942625 filterStream =new SoundFilterStream_2942625(stream,filter);
					stream = new AudioInputStream(filterStream,format,stream.getFrameLength());
				}
				line.open(stream);
				line.start();
				Thread.sleep(100);
				while (line.isRunning()){
					Thread.sleep(100);
				}
				line.close();
			}while (loop);
		}
		catch (Exception e) {	}
		finished = true;

	}
}
