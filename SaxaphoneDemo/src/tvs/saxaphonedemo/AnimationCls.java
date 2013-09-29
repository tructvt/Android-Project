package tvs.saxaphonedemo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AnimationCls implements AnimationListener {
	private Context context;
	private ImageView imageView;
	private RelativeLayout viewGroup;

	public AnimationCls(Context context, ImageView imageView) {
		this.context = context;
		this.imageView = imageView;
		this.viewGroup = viewGroup;

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		((RelativeLayout) this.imageView.getParent())
				.removeView(this.imageView);

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

}
