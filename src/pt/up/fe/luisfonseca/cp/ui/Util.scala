package pt.up.fe.luisfonseca.cp.ui

import android.content.Intent
import android.os.Bundle

object Util {
  
    /**
     * Converts an intent into a {@link Bundle} suitable for use as fragment
     * arguments.
     * 
     * @param intent
     * @return the bundle with the argument
     */
    def intentToFragmentArguments(intent: Intent): Bundle = {
        val arguments = new Bundle();
        if (intent == null) {
            return arguments;
        }
        
        val data = intent.getData();
        if (data != null) {
            arguments.putParcelable("URL_INTENT", data);
        }

        val extras = intent.getExtras();
        if (extras != null) {
            arguments.putAll(intent.getExtras());
        }
        return arguments;
    }

}