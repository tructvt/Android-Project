package tsv.com.vssandroid;

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

public class SoundPlayer implements OnPreparedListener, OnErrorListener,
		OnCompletionListener {
	static final String FILEFORMAT = ".wav";
	public MediaPlayer mediaPlayer;

	public SoundPlayer() {
		super();
		this.mediaPlayer = new MediaPlayer();
	}

	public SoundPlayer(View view, Context context) {
		super();

	}

	public void loadAudio() {
		this.mediaPlayer.reset();
		this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			this.mediaPlayer
					.setDataSource("/sdcard/DCIM/vss/data/output_speech.wav");
			this.mediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 
	public void play() {
		// this.hanlder.removeCallbacks(this);
		if (this.mediaPlayer.isPlaying()) {
			this.mediaPlayer.pause();
			this.mediaPlayer.seekTo(0);
		}
		this.mediaPlayer.setVolume(1, 1);
		// this.mediaPlayer.seekTo(0);
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
