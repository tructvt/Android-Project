package tsv.com.vssandroid;

import android.util.Log;
import android.widget.TextView;

public class NativeCode {
	TextView textView;

	public NativeCode(TextView textView) {
		this.textView = textView;
	}

	public NativeCode() {
	}

	static {
		System.loadLibrary("vss_cpp_android");
	}

	public native String vss(String s, int i);

	public native void vssinit(int i);


}
