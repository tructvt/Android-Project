package tvs.saxaphonedemo;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URI;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.shapes.Shape;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class SoundPlayer implements Runnable, OnPreparedListener,
		OnErrorListener, OnCompletionListener {
	static final String FILEFORMAT = ".mp3";
	public MediaPlayer mediaPlayer;
	private Context context;

	public SoundPlayer(Context context) {
		super();
		this.context = context;
		this.mediaPlayer = new MediaPlayer();
	}

	public SoundPlayer(View view, Context context) {
		super();

	}

	public void loadAudio(View view) {
		this.mediaPlayer.reset();
		// this.mediaPlayer = null;
		int tone = 0;
		this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			AssetFileDescriptor afd = context.getAssets().openFd(
					view.getTag() + FILEFORMAT);
			try {
				this.mediaPlayer.setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());
				this.mediaPlayer.prepare();
			} finally {
				afd.close();
			}
		} catch (IOException e) {
			Log.wtf("Failed to load tone: " + tone, e);
		}
	}

	@Override
	public void run() {
		// this.play();
	}

	public void play() {
		// this.hanlder.removeCallbacks(this);
		if (this.mediaPlayer.isPlaying()) {
			this.mediaPlayer.pause();
			this.mediaPlayer.seekTo(0);
		}
		this.mediaPlayer.setVolume(1, 1);
		this.mediaPlayer.seekTo(0);
		this.mediaPlayer.start();
	}

	public void stop() {
		Log.d("D", "Is playing");
		if (this.mediaPlayer.isPlaying()) {
			Log.d("D", "Hanlder a ");
			this.mediaPlayer.pause();
			this.mediaPlayer.seekTo(0);
			// this.mediaPlayer.stop();
			// this.mediaPlayer.release();
			// this.mediaPlayer = null;

		}

		// onsop();
	}

	private int getToneResource(String tone, Boolean sharp) {
		if (tone == null) {
			return -1;

			// } else if (tone.equals("A")) {
			// if (sharp)
			// return R.raw.a5;
			// return R.raw.a4;
			// } else if (tone.equals("B")) {
			// if (sharp)
			// return R.raw.b5;
			// return R.raw.b4;
			//
			// } else if (tone.equals("C")) {
			// if (sharp)
			//
			// return R.raw.c5;
			// return R.raw.c4;
			// } else if (tone.equals("D")) {
			// if (sharp)
			//
			// return R.raw.d5;
			// return R.raw.d4;
			//
			// } else if (tone.equals("E")) {
			// if (sharp)
			// return R.raw.e5;
			// return R.raw.e4;
			// } else if (tone.equals("F")) {
			// if (sharp)
			// return R.raw.f5;
			// return R.raw.f4;
			// } else if (tone.equals("G")) {
			// if (sharp)
			// return R.raw.g5;
			// return R.raw.g4;

		} else {
			return 0;
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		Log.d("D", "Finished playback");
		// this.currentVolume = MAX_VOLUME;
		this.mediaPlayer.setVolume(1, 1);
		this.mediaPlayer.seekTo(0);
		// this.fadeOutHandler.removeCallbacks(this);
	}

}
