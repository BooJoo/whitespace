package de.fuberlin.sms;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class SMSActivity extends Activity {
    
	Uri uri;
	String text;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try{
		//	String empf = getIntent().getExtras().getString("empfaenger");
			String tmp = getIntent().getStringExtra("empfaenger");
			String tmp2 = getIntent().getStringExtra("text");
			//String text = getIntent().getExtras().getString("text");
			if(tmp != null) 
	     	{
				uri = Uri.parse(tmp);
	     	}
	     	else{
	     		
	     	}
			if(tmp2 != null){
				text = tmp2;//savedInstanceState.getString("text");
			}
		}
		catch(Exception e){ uri = Uri.parse("smsto:01234556"); System.out.println(e);}
		
		if(uri == null){
			// Starte Kontaktauswahl
			Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
					ContactsContract.Contacts.CONTENT_URI);// Phone.CONTENT_URI);
			startActivityForResult(contactPickerIntent, 1001);
		}else{
			// Starte SMS
			
			Intent it = new Intent(Intent.ACTION_SENDTO, uri);
			it.putExtra("sms_body", text);
			startActivity(it);
		}
	

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == 1001) {
			if (resultCode == RESULT_OK) {
				// Kontakt wurde ausgewählt
				Cursor cursor = null;
				String phoneNumber = "";
				List<String> allNumbers = new ArrayList<String>();
				int phoneIdx = 0;
				try {
					Uri result = data.getData();
					String id = result.getLastPathSegment();
					cursor = getContentResolver().query(Phone.CONTENT_URI,
							null, Phone.CONTACT_ID + "=?", new String[] { id },
							null);
					phoneIdx = cursor.getColumnIndex(Phone.DATA);
					if (cursor.moveToFirst()) {
						while (cursor.isAfterLast() == false) {
							phoneNumber = cursor.getString(phoneIdx);
							allNumbers.add(phoneNumber);
							cursor.moveToNext();
						}
					} else {
						// no results actions
					}
				} catch (Exception e) {
					// error actions
				} finally {
					if (cursor != null) {
						cursor.close();
					}
				}
				final CharSequence[] items = allNumbers
						.toArray(new String[allNumbers.size()]);

				try {
					// Packe URL zur ersten Telefonnummer des Kontaktes
					uri = Uri.parse("smsto:" + allNumbers.get(0));
				} catch (Exception e) {
					// Kontakt hat keine Telefonnummer
					uri = Uri.parse("smsto:015122822377");

				}
				// Starte SMS Versand
				Intent it = new Intent(Intent.ACTION_SENDTO, uri);
				it.putExtra("sms_body", "The SMS text");
				startActivity(it);
			}

		}
	}
}
