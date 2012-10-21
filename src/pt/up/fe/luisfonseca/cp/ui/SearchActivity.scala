package pt.up.fe.luisfonseca.cp.ui

import android.app.ActionBar
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import pt.up.fe.luisfonseca.cp.ui.MainActivity

class SearchActivity extends Activity {

    override def onCreate(savedInstanceState: Bundle) = {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        val actionBar = getActionBar
        getActionBar().setDisplayHomeAsUpEnabled(true)
        actionBar.setNavigationMode(ActionBar.DISPLAY_SHOW_TITLE)
        val intent = getIntent
        val from = intent.getStringExtra("from")
        val to = intent.getStringExtra("to")
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time").trim()
        val traintype = intent.getStringExtra("type").trim()

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

    override def onOptionsItemSelected(item: MenuItem) = {
        if (item.getItemId == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, classOf[MainActivity]))
            true
        }
        else
        	super.onOptionsItemSelected(item)
    }
}