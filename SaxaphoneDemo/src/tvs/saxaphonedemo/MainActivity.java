package tvs.saxaphonedemo;

import java.io.IOException;

import android.R.animator;
import android.R.color;
import android.R.integer;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener,
		OnClickListener {

	private SoundPlayer soundPlayer;
	private Notes note;
	private Boolean isFreg = true;
	private Button btnFreg;
	private RelativeLayout relative;
	int create = 15;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Config screen| Full, No title, Screen On
		// xin chao cac ban
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		super.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.setContentView(R.layout.main);

		// Asign view
		relative = (RelativeLayout) findViewById(R.id.id_main_relative);
		relative.setBackground(this.getResources().getDrawable(R.drawable.sax));
		btnFreg = (Button) findViewById(R.id.id_freg);
		// Set on click button
		btnFreg.setOnClickListener(this);

		// initial
		this.init();
	}

	public void init() {
		this.initButton((Button) findViewById(R.id.id_do4));
		this.initButton((Button) findViewById(R.id.id_fa4));
		this.initButton((Button) findViewById(R.id.id_la4));
		this.initButton((Button) findViewById(R.id.id_mi4));
		this.initButton((Button) findViewById(R.id.id_re4));
		this.initButton((Button) findViewById(R.id.id_sol4));
		this.initButton((Button) findViewById(R.id.id_xi4));
		this.initButton((Button) findViewById(R.id.id_do5));
		this.initButton((Button) findViewById(R.id.id_fa5));
		this.initButton((Button) findViewById(R.id.id_la5));
		this.initButton((Button) findViewById(R.id.id_mi5));
		this.initButton((Button) findViewById(R.id.id_re5));
		this.initButton((Button) findViewById(R.id.id_sol5));
		this.initButton((Button) findViewById(R.id.id_xi5));

	}

	private void initButton(Button btn) {
		// Button btn;
		// Get Height and width
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		int buttonHeight = (int) height / 7;
		// btn.setBackground(this.getResources().getDrawable(R.drawable.note));
		int buttonWidth = (int) width / 5;
		btn.setHeight(buttonHeight);
		btn.setMaxHeight(buttonHeight);
		btn.setMaxWidth(buttonWidth);
		btn.setWidth(buttonWidth);
		btn.setOnTouchListener(this);
	}

	public void playAnimal(View view, int pos) {
		// Container
		RelativeLayout re = new RelativeLayout(this);
		// re.setBackground(getResources().getDrawable(R.drawable.border));
		// re.setVisibility(re.INVISIBLE);
		re.setLayoutParams(relative.getLayoutParams());
		// View
		ImageView imgView = new ImageView(this.getApplicationContext());
		imgView.setBackground(this.getResources().getDrawable(R.drawable.note));
		imgView.setLayoutParams(view.getLayoutParams());
		imgView.setVisibility(ImageView.VISIBLE);

		// View layout config
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				view.getLayoutParams());
		params.topMargin = (int) view.getTop();
		params.leftMargin = (int) view.getLeft();
		params.width = view.getWidth();
		params.height = view.getHeight();

		// Log.d("sax", "" + params.topMargin);
		// Log.d("sax", "" + params.leftMargin);
		// Log.d("sax", "" + view.getX());
		// Log.d("sax", "" + view.getY());

		imgView.setLayoutParams(params);
		re.addView(imgView);
		relative.addView(re);

		Animation animation = null;
		// Check Hint Buttin
		Button btn = (Button) view;
		float intHint = Float.parseFloat((String) btn.getHint().toString());
		if ((intHint > 550 && intHint < 700)
				|| (intHint > 290 && intHint < 350))
			animation = AnimationUtils.loadAnimation(getApplicationContext(),
					R.anim.note_down);
		else animation = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.note_up);
		imgView.startAnimation(animation);
		AnimationCls animationCls = new AnimationCls(this, imgView);
		animation.setAnimationListener(animationCls);
	}

	@Override
	protected void onResume() {
		super.onResume();
		soundPlayer = new SoundPlayer(this.getApplicationContext());
		note = new Notes();
	}

	@Override
	protected void onPause() {
		super.onPause();
		soundPlayer.stop();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			RelativeLayout re = new RelativeLayout(this);
			// re.setBackground(getResources().getDrawable(R.drawable.border));
			re.setLayoutParams(relative.getLayoutParams());
			// play animal
			playAnimal(v, create);
			Log.d("sax", "ID:" + ((Button) v).getHint());
			if (isFreg) {
				this.soundPlayer.loadAudio(v);
				this.soundPlayer.play();
			} else {
				note.playSound((Button) v);
			}
			return true;
		case MotionEvent.ACTION_UP:
			// note.playSound((Button) v);
			return true;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		if (btnFreg.getText().toString().equalsIgnoreCase("freg")) {
			isFreg = true;
			btnFreg.setText("wav");
		} else {

			isFreg = false;
			btnFreg.setText("freg");
		}

	}

}
