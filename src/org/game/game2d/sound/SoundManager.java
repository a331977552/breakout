package org.game.game2d.sound;

import org.game.game2d.sound.filters.AbstractSoundFilter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * singleton instance.
 * use thread pool to play music. by doing so, it can save thread resources
 */
public class SoundManager {
    private static final SoundManager singleton = new SoundManager();
    final ExecutorService threadPool;

    private SoundManager(){
        threadPool = Executors.newCachedThreadPool();

    }
    public static SoundManager getInstance(){
        return singleton;
    }
    public void play(String fileName, boolean loop, AbstractSoundFilter filter){
        threadPool.execute(new Sound(fileName,loop, filter));
    }
    public void play(String fileName){
        this.play(fileName, false,null);
    }

}
