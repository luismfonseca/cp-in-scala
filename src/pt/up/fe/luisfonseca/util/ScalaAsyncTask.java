package pt.up.fe.luisfonseca.util;

import android.os.AsyncTask;

public abstract class ScalaAsyncTask<T1, T2, T3>
	extends AsyncTask<T1, T2, T3>
{
    protected T3 doInBackground(T1... f) {
        return doInBackground(f[0]); // Scala fix
    }
    
    abstract protected T3 doInBackground(T1 f);
}