package tsv.com.vssandroid;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import tsv.com.vssandroid.NativeCode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	final String TAG = "vss";
	EditText etText;
	TextView tvLogCat;
	Button btnSpeak, btnPlayAudio, btnClear;
	RelativeLayout rllGobal;
	NativeCode cppAndroid;// = new NativeCode();;
	int load;
	String strOldLog;
	SoundPlayer sp;

	// Process process;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.ClearLogcat();
		setContentView(R.layout.vss_layout);
		this.init();
		Log.d("vss1", "resume1");
		btnSpeak.setOnClickListener(this);
		btnPlayAudio.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		Log.d("vss1", "onsreate");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("vss1", "resume");
		load = 0;
		cppAndroid = new NativeCode();
		cppAndroid.vss("", load);

		// Write Logcat.
		this.runLogcat();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Toast.makeText(this, "Changed", Toast.LENGTH_SHORT).show();
		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSpeak_id:
			load = 1;
			// Check text null
			if (!checkText()) {
				etText.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.border_text_error));
				return;
			}
			etText.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.border));
			cppAndroid.vss(etText.getText().toString().trim(), load);
			this.runLogcat();
			// logcat.execute();
			sp.loadAudio();
			sp.play();
			break;

		case R.id.btnClear_id:
			this.ClearLogcat();
			tvLogCat.setText("");
			break;
		case R.id.btnPlayAudio_id:
			sp.loadAudio();
			sp.play();
			break;
		}

	}

	public void init() {
		// Init view
		tvLogCat = (TextView) findViewById(R.id.tvLog_id);
		btnSpeak = (Button) findViewById(R.id.btnSpeak_id);
		btnPlayAudio = (Button) findViewById(R.id.btnPlayAudio_id);
		btnClear = (Button) findViewById(R.id.btnClear_id);
		etText = (EditText) findViewById(R.id.etText_id);
		rllGobal = (RelativeLayout) findViewById(R.id.rllRelative_id);
		tvLogCat.setText("");
		load = 0;
//		cppAndroid = new NativeCode();
		sp = new SoundPlayer();
		strOldLog = "";
		// DisplayMetrics
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		// Config text move
		etText.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.border));
		etText.setText("xin chào quý vị");
		btnSpeak.setHeight(height / 7);
		btnSpeak.setWidth(height / 7);
		btnPlayAudio.setHeight(height / 7);
		btnPlayAudio.setWidth(height / 7);
		btnClear.setHeight(height / 7);
		btnClear.setWidth(height / 7);

	}

	public Boolean checkText() {
		if (etText.getText().toString().trim().equalsIgnoreCase("")) {
			return false;
		}
		return true;
	}

	public void ClearLogcat() {
		String[] command = { "logcat", "-c" };
		strOldLog = "";
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String runLogcat() {
		// this.ClearLogcat();
		Process process;
		String strToText = strOldLog;
		String newLine = "";
		// while (true) {
		StringBuilder sblog = new StringBuilder("");
		try {
			String[] command = { "logcat", "-d", TAG + ":I *:S" };
			process = Runtime.getRuntime().exec(command);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				sblog.append(line + "\n");

			}
			if (strToText.length() != sblog.toString().length()) {
				// Log.d("vss1", "strToText:"+log.toString().length());
				// Log.d("vss1", "strToText:"+strToText.length() );
				newLine = sblog.toString().substring(strToText.length(),
						sblog.toString().length());
				strOldLog += newLine;
			}

			// makeup log
			String strRemove;
			String log = newLine;
			strRemove = log.substring(log.indexOf("("), log.indexOf("):") + 1)
					.toString();
			log = log.replace(strRemove, "");

			// Set text color
			SpannableString strSpan = new SpannableString(log);
			if (log.indexOf("E/vss") > 0) {

				int startIndex = 0;
				int endIndex = log.indexOf("E/vss", startIndex);
				while (log.indexOf("\n", startIndex) > 0) {
					strSpan.setSpan(new ForegroundColorSpan(Color.RED),
							endIndex, log.indexOf("\n", endIndex), 0);
					startIndex = endIndex + 6;
					if (log.indexOf("E/vss", startIndex) < 0)
						break;
					endIndex = log.indexOf("E/vss", startIndex);

				}
				// Set Log

			}
			tvLogCat.append(strSpan);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Set end line Scroll
		ScrollView sv = (ScrollView) findViewById(R.id.svScrollLog_id);
		// sv.setScrollY(sv.getHeight());
		sv.fullScroll(ScrollView.FOCUS_DOWN);
		// sv.scrollBy(0, +sv.getHeight());
		return sblog.toString();
		// }
	}

}
