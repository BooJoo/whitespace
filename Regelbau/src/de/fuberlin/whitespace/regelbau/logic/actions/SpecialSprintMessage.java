package de.fuberlin.whitespace.regelbau.logic.actions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import de.fuberlin.whitespace.KnightRider;
import de.fuberlin.whitespace.RuleMainview;

public class SpecialSprintMessage extends ShowMessage{
	KnightRider rider;
	private Context context;
	private Button button;
	public SpecialSprintMessage(Context context,String[] args) {
		super(args);
		this.context = context;
		this.button = button;
		rider = new KnightRider(context);
	}

	 @Override
    public void Do() {
		Thread thread = new Thread(new MakeFun(rider,context));
		thread.start();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -7649290268306342354L;

	class MakeFun implements Runnable{
		KnightRider rider;
		private Context context;

		
		public MakeFun(KnightRider rider, Context context) {
			this.context = context;
			this.rider = rider;
		
		}

		@Override
		public void run() {
			try {
				Thread.sleep(1000);
				rider.say("Maikel!");
				Thread.sleep(1000);
				rider.say("Die Welt ist in Gefahr! Das neue Ei o es 7 ist hässlich wie die Nacht und" +
						"Äppel braucht unsere Unterstützung!" +
						"Wir müssen sofort los nach Kupertino!" +
						"Team Weitspejß zur Rettung!");
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
