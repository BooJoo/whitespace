package de.fuberlin.whitespace.regelbau.logic.actions;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import de.fuberlin.whitespace.regelbau.R;
import de.fuberlin.whitespace.regelbau.logic.Action;

public class ShowMessage extends Action {
    
    /**
     * 
     */
    private static final long serialVersionUID = -5113572234174380962L;
    
    @Override
    public void Do (Context context) {
    	//Ton wird abgespielt, wenn Regel triggert
    	MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.notification);
	    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	    try {
			mPlayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    mPlayer.start();
		new AlertDialog.Builder(context)
			.setTitle("Erinnerung")
			.setMessage(String.valueOf(this.getParam("message")))
			.setNeutralButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
	
	                	    @Override
	                	    public void onClick(DialogInterface arg0,
	                		    int arg1) {
	                		
	                	    }
				}
			).show();
	
	    }
    
}
