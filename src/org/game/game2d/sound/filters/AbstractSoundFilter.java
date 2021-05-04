package org.game.game2d.sound.filters;

/**
 * base class for creating different sound filter.
 * filters such as echo, fade filters implement filter method to implement its own filter.
 */
public abstract class AbstractSoundFilter {



    public abstract void filter(int bytesRead, byte[] samples);

    public static short getSample(byte[] buffer, int position) {
        return (short) (((buffer[position + 1] & 0xff) << 8) | (buffer[position] & 0xff));
    }

    public static void setSample(byte[] buffer, int position, short sample) {
        buffer[position] = (byte) (sample & 0xff);
        buffer[position + 1] = (byte) ((sample >> 8) & 0xff);
    }
}
