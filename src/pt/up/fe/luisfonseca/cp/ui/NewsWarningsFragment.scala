package pt.up.fe.luisfonseca.cp.ui

import java.util.ArrayList
import pt.up.fe.luisfonseca.cp.api.NewsLoader
import pt.up.fe.luisfonseca.cp.api.ResponseHandler
import pt.up.fe.luisfonseca.cp.api.WarningsLoader
import pt.up.fe.luisfonseca.cp.api.json.News
import pt.up.fe.luisfonseca.cp.api.json.Warnings
import android.app.ActionBar
import android.app.ActionBar.Tab
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TabHost;

class NewsWarningsFragment
	extends Fragment
	with ActionBar.TabListener
{
    var mTabHost: TabHost = null
    var mNewsLoader: NewsLoader = null
    var mWarningsLoader: WarningsLoader = null

    var mNewsAdapter: ArrayAdapter[String] = null
    var mWarningsAdapter: ArrayAdapter[String] = null
    
    var mDemoCollectionPagerAdapter: DemoCollectionPagerAdapter = null
    var mViewPager: ViewPager = null
    var mlvNews: ListView = null
    var mlvWarnings: ListView = null
    
    override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {
        super.onCreate(savedInstanceState)
        mNewsLoader = new NewsLoader(new ResponseHandlerImplementation())
        
        mWarningsLoader = new WarningsLoader(new ResponseHandler[Warnings]() {
    		def onError(error: ResponseHandler.Error.Type) {
    		}
    		
    		def onResultReceived(result: Warnings) {
    		  result.today.foreach(w => mWarningsAdapter.add(w.title))
				
				mWarningsAdapter.notifyDataSetChanged
			}

		});
        mNewsLoader.execute
        mWarningsLoader.execute

        val v = inflater.inflate(R.layout.fragment_newswarnings, container, false);

        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getActivity().getSupportFragmentManager())
        mViewPager = v.findViewById(R.id.viewpager).asInstanceOf[ViewPager]
        mViewPager.setAdapter(mDemoCollectionPagerAdapter)
        

		mNewsAdapter = new ArrayAdapter[String](v.getContext(), android.R.layout.simple_list_item_1, new ArrayList[String])

        
		mWarningsAdapter = new ArrayAdapter[String](v.getContext(), android.R.layout.simple_list_item_1, new ArrayList[String])
		mlvWarnings = new ListView(getActivity())
		mlvWarnings.setAdapter(mNewsAdapter)
        v;
    }

    
	def onTabReselected(tab: Tab, arg1: FragmentTransaction) {
	}

	def onTabSelected(tab: Tab, arg1: FragmentTransaction) {
	}

	def onTabUnselected(tab: Tab, arg1: FragmentTransaction) {
	}
	
	class ResponseHandlerImplementation
		extends ResponseHandler[News]
	{
		def onError(error: ResponseHandler.Error.Type) = {
			// TODO Auto-generated method stub
		}
		
		def onResultReceived(result: News) = {
			result.news.foreach(n => mNewsAdapter.add(n.title))

			mNewsAdapter.notifyDataSetChanged()
		}
	}


	class DemoCollectionPagerAdapter(fm: FragmentManager)
		extends FragmentPagerAdapter(fm)
	{
	    override def getItem(i: Int) = {
	        val f = new TabListViewFragment();
	        val b = new Bundle();
			mlvNews = new ListView(getActivity());
			mlvNews.setAdapter(mNewsAdapter);
	        b.putInt(TabListViewFragment.LIST_VIEW, i);
	        f.setArguments(b);
	        f
	    }
	
	    override def getCount = 2
	
	    override def getPageTitle(position: Int) =
	      if(position == 0) "News" else "Warnings"
	        
	}
	
	object TabListViewFragment{
	  val LIST_VIEW = "ListViewID"
	}

	class TabListViewFragment extends Fragment {
	  
	    override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {
	    	val lv = new ListView(getActivity());
	    	val i = getArguments().getInt(TabListViewFragment.LIST_VIEW);
	    	if(i == 0)
				lv.setAdapter(mNewsAdapter);
			else
				lv.setAdapter(mWarningsAdapter);
	    	
	    	lv;
	    }
	}
}