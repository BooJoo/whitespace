package de.fuberlin.whitespace;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;

import de.fuberlin.whitespace.regelbau.R;

public class FacebookActivity extends Activity {

    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.facebook);

	this.msg = getIntent().getStringExtra("message");
	
	/* Die folgenden Zeilen können verwendet werden, um einen Hash-Key für den API-Zugang
	 * zu generieren. Der von Facebook vorgeschlagene Weg scheitert an einem Problem mit der
	 * auf den meisten Systemen vorhandenen OpenSSL-Implementierung.
	 try {
	        PackageInfo info = getPackageManager().getPackageInfo("de.fuberlin.whitespace.regelbau",
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("YOURHASH KEY:",
	                    Base64.encodeToString(md.digest(), Base64.DEFAULT));
	        }
	    } catch (NameNotFoundException e) {
		e.printStackTrace();
	    } catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
	    }
	 */
	
	Session.openActiveSession(this, true, new Session.StatusCallback() {

	    @Override
	    public void call(Session session, SessionState state, Exception exception) {

		if (session.isOpened()) {
		    if (!hasPublishPermission()) {
			session.requestNewPublishPermissions(new Session.NewPermissionsRequest(FacebookActivity.this, Arrays.asList("publish_actions")));
		    }
		    postStatusUpdate();
		} else if (exception != null) {
		    exception.printStackTrace();
		    finish();
		}
	    }
	});
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    private boolean hasPublishPermission() {
	Session session = Session.getActiveSession();
	return session != null && session.getPermissions().contains("publish_actions");
    }

    /**
     * Senden des Facebook-Posts in {@link msg}.
     */
    private void postStatusUpdate() {
	Request request = Request
		.newStatusUpdateRequest(Session.getActiveSession(), this.msg, new Request.Callback() {
		    @Override
		    public void onCompleted(Response response) {

			if (response.getError() == null) {
			    System.out.println("success");
			} else {
			    System.out.println("failure: " + response.getError().getErrorMessage());
			}
			finish();
		    }
		});
	request.executeAsync();
    }
}
