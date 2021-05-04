package org.game.game2d.sound;

import org.game.game2d.sound.filters.AbstractSoundFilter;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundFilterStream extends FilterInputStream {
    private AbstractSoundFilter soundFilter;

    public SoundFilterStream(InputStream in, AbstractSoundFilter soundFilter) {
        super(in);
        this.soundFilter = soundFilter;
    }


    @Override
    public int read(byte[] sample, int offset, int length) throws IOException {
        int bytesRead = super.read(sample, offset, length);//bytesRead = length in FilterInputStream;
        soundFilter.filter(bytesRead,sample);
        return length;
    }
}