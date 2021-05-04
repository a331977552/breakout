package org.game.game2d.sound;

import org.game.game2d.sound.filters.AbstractSoundFilter_2942625;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundFilterStream_2942625 extends FilterInputStream {
    private AbstractSoundFilter_2942625 soundFilter;

    public SoundFilterStream_2942625(InputStream in, AbstractSoundFilter_2942625 soundFilter) {
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