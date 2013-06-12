package de.fuberlin.whitespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;
import de.fuberlin.whitespace.regelbau.Main;
import de.fuberlin.whitespace.regelbau.R;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.actions.ShowMessage;

public class RuleMainview extends Activity {

    MyAdapter myadapter;

    private static RuleMainview _instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_rule_mainview);
	myadapter = new MyAdapter(this,this);
	GridView gridview = (GridView) findViewById(R.id.gridView1);
	gridview.setAdapter(myadapter);

	_instance = this;
    }


    public void rufeRegelkontruktionAuf(){
	Intent i = new Intent(getApplicationContext(), Main.class);
	startActivityForResult(i, 666);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// Check which request we're responding to
	if (requestCode == 666) {
	    // Make sure the request was successful
	    if (resultCode == RESULT_OK) {

		// satzanzeige.setButtonLabelZwei(res);
		//Rule rule = (Rule)data.getSerializableExtra("regel");
		String[] res = data.getStringArrayExtra("selectedItems");
		System.out.println(res[0]+" "+res[1]+" "+res[2]);
	    }
	}
    }

    public static RuleMainview getInstance () {
	return _instance;
    }

}
