package org.game.game2d.sound.filters;

public class FadeFilterAbstract_2942625 extends AbstractSoundFilter_2942625 {
    @Override
    public void filter(int bytesRead, byte[] samples) {
        float change = 2.0f * (1.0f / (float) bytesRead);
        float volume = 1.0f;
        short amp = 0;
        // Loop through the sample 2 bytes at a time
        for (int i = 0; i < bytesRead; i += 2) {
            amp = getSample(samples, i);
            amp = (short) ((float) amp * volume);
            setSample(samples, i, amp);
            volume = volume - change;
        }
    }
}
