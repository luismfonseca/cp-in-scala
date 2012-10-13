package pt.up.fe.luisfonseca.cp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Util {

    /**
     * Converts an intent into a {@link Bundle} suitable for use as fragment
     * arguments.
     * 
     * @param intent
     * @return the bundle with the argument
     */
    public static Bundle intentToFragmentArguments(Intent intent) {
        Bundle arguments = new Bundle();
        if (intent == null) {
            return arguments;
        }
        
        final Uri data = intent.getData();
        if (data != null) {
            arguments.putParcelable("URL_INTENT", data);
        }

        final Bundle extras = intent.getExtras();
        if (extras != null) {
            arguments.putAll(intent.getExtras());
        }
        return arguments;
    }
}
