package de.fuberlin.whitespace;

import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.sax.StartElementListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {
	private Context mContext;
	
	public MyAdapter(Context c) {
		mContext = c;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 6; 
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8, 8, 8, 8);
            if(position % 2 != 0){
            	imageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(mContext,RuleConstruction.class);
						mContext.startActivity(i); 
					}
				});
            	//buttonView.setText("Aktion");
            	imageView.setLayoutParams(new GridView.LayoutParams(125,125));
            }else{
            	//buttonView.setText("Auslšser");
            	imageView.setLayoutParams(new GridView.LayoutParams(125,125));
            }
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
	}
	 // references to our images
    private Integer[] mThumbIds = {
         R.drawable.a9,R.drawable.a1,
         R.drawable.a7,R.drawable.a2,
         R.drawable.a6,R.drawable.a4
    };
}
