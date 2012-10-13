package pt.up.fe.luisfonseca.cp.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SearchActivity extends Activity {

    //final ActionBar mActionBar = getActionBar();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        final ActionBar actionBar = getActionBar();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.DISPLAY_SHOW_TITLE);
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time").trim();
        String type = intent.getStringExtra("type").trim();

        final CharSequence title = from;
        //actionBar.setTitle(title);
    }
    /*
    protected Fragment onCreatePane() {
            return new DummySectionFragment();
    }
    
    protected void onNewIntent(Intent iQuery) {
        String query = iQuery.getStringExtra(SearchManager.QUERY).trim();
        final CharSequence title = getString(R.string.title_search_query, query);
        mActionBar.setTitle(title);
        DummySectionFragment dsf = new DummySectionFragment();
        dsf.setArguments(intentToFragmentArguments(iQuery));
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.root_container, dsf)
            .commit();*//*
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
