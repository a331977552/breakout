package org.game.game2d.sound.filters;

public class EchoFilterAbstract_2942625 extends AbstractSoundFilter_2942625 {
    //put it in variable to prevent frequently allocating memory and release
    private final short[] delayBuffer;
    private final float decay;
    private int delayBufferIndex;


    public EchoFilterAbstract_2942625(int numDelaySamples, float decay) {
        delayBuffer = new short[numDelaySamples];
        this.decay = decay;
    }

    @Override
    public void filter(int bytesRead, byte[] samples) {
        for (int i = 0; i < bytesRead; i += 2) {
            //get origin sample
            short oldSample = getSample(samples, i);
            short newSample = (short) (oldSample + decay
                    * delayBuffer[delayBufferIndex]);
            //set changed samples
            setSample(samples, i, newSample);
            // update delay buffer
            delayBuffer[delayBufferIndex] = newSample;
            delayBufferIndex++;
            //reset index to resue the buffer.
            if (delayBufferIndex == delayBuffer.length) {
                delayBufferIndex = 0;
            }
        }
    }
}
