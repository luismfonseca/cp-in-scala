package pt.up.fe.luisfonseca.cp.ui

import android.app.ActionBar
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView;

class MainActivity
	extends FragmentActivity with ActionBar.OnNavigationListener
{
	private val STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item"
    
    override def onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the action bar.
        val actionBar = getActionBar()
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST)

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
            new ArrayAdapter[String](
                    actionBar.getThemedContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    Array[String](
                            getString(R.string.title_section1),
                            getString(R.string.title_section2),
                            getString(R.string.title_section3),
                            getString(R.string.title_section4),
                            getString(R.string.title_section5)
                    )),
            this)
    }

    override def onRestoreInstanceState(savedInstanceState: Bundle) {
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    override def onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }

    
    override def onCreateOptionsMenu(menu: Menu): Boolean = {
        getMenuInflater().inflate(R.menu.activity_main, menu)
        val mGetTrainsItem = menu.findItem(R.id.buttonSearch)
        if (mGetTrainsItem != null)
          mGetTrainsItem.setVisible(false);
        super.onCreateOptionsMenu(menu)
    }
    
	override def onPrepareOptionsMenu(menu: Menu) = {
	
	    super.onPrepareOptionsMenu(menu)
	}
    
    override def onNavigationItemSelected(position: Int, id: Long) = {
        // When the given tab is selected, show the tab contents in the container
        val fragment = position match
	        {
		    	case 0 => new SearchFragment()
		    	case 1 => new DummySectionFragment()
		    	case 3 => new NewsWarningsFragment()
		    	case _ => new DummySectionFragment()
	        }
        
        val args = new Bundle()
        args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1)
        fragment.setArguments(args)
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        true
    }

    object DummySectionFragment {
      
	    val ARG_SECTION_NUMBER = "section_number";
    }

	/**
	 * A dummy fragment representing a section of the app, but that simply displays dummy text.
	 */
	class DummySectionFragment extends Fragment {
	
	    override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) : View = {
	        val textView = new TextView(getActivity());
	        textView.setGravity(Gravity.CENTER);
	        val args = getArguments();
	        textView.setText(Integer.toString(args.getInt(DummySectionFragment.ARG_SECTION_NUMBER)) + " wheres the love? :)");
	        textView;
	    }
	}

}