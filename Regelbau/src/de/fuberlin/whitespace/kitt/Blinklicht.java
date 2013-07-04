package de.fuberlin.whitespace.kitt;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * Klasse, die ein KnightRider Blinklicht anzeigt.
 * @author Christoph
 *
 */
public class Blinklicht extends LinearLayout {
	View[] v;
	Context context;
	
	public Blinklicht(Context context) {
		super(context);
		this.context = context;
		setLayoutParams(new LinearLayout.LayoutParams(-1,36));
		v = new View[10];
		for(int i = 0; i < v.length; i++){
			View view = new View(context);
			view.setLayoutParams(new android.view.ViewGroup.LayoutParams(60,-1));
			int j = i;
			if(i < 5){
				j = j*20;
			}else{
				j = (9-j)*20;
			}
			view.setBackgroundColor(Color.rgb(j+20,0,0)); 
			v[i] = view;
			addView(v[i]);
		} 
		animation();
	}
	
	/**
	 * Fügt den Views die Animation hinzu.
	 */
	public void animation(){
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		final Animation run = new TranslateAnimation(-display.getWidth()/2.4f, display.getWidth()/2, 0.0f, 0.0f);
		run.setDuration(1500);
		
		run.setRepeatCount(Animation.INFINITE);
		run.setRepeatMode(Animation.REVERSE);
		run.setFillBefore(true);
		for(View vi : v) vi.setAnimation(run); 
	}

}
