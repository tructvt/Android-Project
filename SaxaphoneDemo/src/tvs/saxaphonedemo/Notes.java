package tvs.saxaphonedemo;

import android.R.string;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Notes {
	private static final int duration = 2; // seconds
	private static final int sampleRate = 8000;
	private static final int numSamples = duration * sampleRate;
	// Notes
//	private final double sDo1Hz = 261.630;// c - do
//	private final double sReHz = 293.66;// d - re
//	private final double sMiHz = 329.63;// e - mi
//	private final double sFaHz = 349.23;// f - fa
//	private final double sSoHz = 392.00;// g - sol
//	private final double sLaHz = 440.00;// a - la
//	private final double sTiHz = 493.88;// b - xi
//	private final double sDo2Hz = 523.25;// c - do2
	private AudioTrack audioTrack;

	public Notes() {

		// TODO Auto-generated constructor stub
	}

	// Gen to tone
	private byte[] genTone(double freq) {
		double[] sample = new double[numSamples];
		for (int i = 0; i < numSamples; ++i)
			sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / freq));

		// convert to 16 bit pcm sound array
		// assumes the sample buffer is normalised.
		byte[] sound = new byte[2 * numSamples];
		int idx = 0;
		for (double dVal : sample) {
			short val = (short) (dVal * 32767);
			sound[idx++] = (byte) (val & 0x00ff);
			sound[idx++] = (byte) ((val & 0xff00) >>> 8);
		}
		return sound;
	}

	// Play sound
	public void playSound(Button btn) {
		// if (view.getTag())

		String note = btn.getHint().toString();
		double freg = Double.parseDouble(note);
		final byte[] play = genTone(freg);
		// (new Thread(new Runnable() {
		// @Override
		// public void run() {
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, 3,
				// AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, numSamples,
				AudioTrack.MODE_STATIC);
		audioTrack.write(play, 0, numSamples);
		audioTrack.play();

		// }

		// })).start();

	}

	public void stopSound() {
		audioTrack.flush();
		audioTrack.stop();
		audioTrack.release();
		// if (audioTrack.getPlayState() == audioTrack.PLAYSTATE_PLAYING) {
		// audioTrack.stop();
		// }
	}
}
