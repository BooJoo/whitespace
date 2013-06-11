package de.fuberlin.whitespace;

import android.content.Context;
import android.provider.MediaStore.Images;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MultipleImagesCell extends LinearLayout {

	Integer[] images;
	double breiteProElement;
	Context c;
	
	public MultipleImagesCell(Context context,Integer[] resources) {
		super(context);
		this.images = resources;
		c = context;
		breiteProElement = 1.0/images.length;
		this.setOrientation(LinearLayout.HORIZONTAL);
		
		init();
	}

	public void init(){
		for(int i = 0; i < images.length; i++){
			ImageView imageView = new ImageView(c);
		    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
	        imageView.setPadding(8, 8, 8, 8);
	        
	        imageView.setLayoutParams(new GridView.LayoutParams(
	        		(int)(200*breiteProElement),150));
	        imageView.setImageResource(images[i]);
	        this.addView(imageView);
		}
	}
}
