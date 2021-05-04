package org.game.game2d.sound;

import org.game.game2d.sound.filters.AbstractSoundFilter_2942625;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * singleton instance.
 * use thread pool to play music. by doing so, it can save thread resources
 */
public class SoundManager_2942625 {
    private static final SoundManager_2942625 singleton = new SoundManager_2942625();
    final ExecutorService threadPool;

    private SoundManager_2942625(){
        threadPool = Executors.newCachedThreadPool();

    }
    public static SoundManager_2942625 getInstance(){
        return singleton;
    }
    public void play(String fileName, boolean loop, AbstractSoundFilter_2942625 filter){
        threadPool.execute(new Sound_2942625(fileName,loop, filter));
    }
    public void play(String fileName){
        this.play(fileName, false,null);
    }

}
