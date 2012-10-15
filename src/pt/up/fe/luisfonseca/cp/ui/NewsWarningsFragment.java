package pt.up.fe.luisfonseca.cp.ui;

import java.util.ArrayList;

import pt.up.fe.luisfonseca.cp.api.NewsLoader;
import pt.up.fe.luisfonseca.cp.api.ResponseHandler;
import pt.up.fe.luisfonseca.cp.api.WarningsLoader;
import pt.up.fe.luisfonseca.cp.api.json.News;
import pt.up.fe.luisfonseca.cp.api.json.Warnings;
import scala.Enumeration.Value;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

public class NewsWarningsFragment extends Fragment
	implements ActionBar.TabListener
{
    TabHost mTabHost;
    NewsLoader mNewsLoader;
    WarningsLoader mWarningsLoader;

    static ArrayAdapter<String> mNewsAdapter;
    static ArrayAdapter<String> mWarningsAdapter;
    
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    ListView mlvNews,
    		 mlvWarnings;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsLoader = new NewsLoader(new ResponseHandlerImplementation());
        
        mWarningsLoader = new WarningsLoader(new ResponseHandler<Warnings>() {
    		public void onError(Value error) {
    			// TODO Auto-generated method stub
    		}
    		
    		public void onResultReceived(Warnings result) {
				//for(Warnings.WarningItem w : results.today)
				//	mWarningsAdapter.add(w.title);
				
				mWarningsAdapter.notifyDataSetChanged();
			}

		});
        mNewsLoader.execute();
        mWarningsLoader.execute();

        View v = inflater.inflate(R.layout.fragment_newswarnings, container, false);

        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) v.findViewById(R.id.viewpager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        

		mNewsAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, new ArrayList<String>());

        
		mWarningsAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, new ArrayList<String>());
		mlvWarnings = new ListView(getActivity());
		mlvWarnings.setAdapter(mNewsAdapter);
        return v;
    }

    
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
	}

	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
	}

	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
	}
	

	private final class ResponseHandlerImplementation implements
			ResponseHandler<News> {
		public void onError(Value error) {
			// TODO Auto-generated method stub
		}
		
		public void onResultReceived(News result) {
			//for(News.NewItem n : results.news())
			//	mNewsAdapter.add(n.title);

			mNewsAdapter.notifyDataSetChanged();
			
		}
	}


	public class DemoCollectionPagerAdapter extends FragmentPagerAdapter {
	    public DemoCollectionPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }
	
	    @Override
	    public Fragment getItem(int i) {
	        Fragment f = new TabListViewFragment();
	        Bundle b = new Bundle();
			mlvNews = new ListView(getActivity());
			mlvNews.setAdapter(mNewsAdapter);
	        b.putInt(TabListViewFragment.LIST_VIEW, i);
	        f.setArguments(b);
	        return f;
	    }
	
	    @Override
	    public int getCount() {
	        return 2;
	    }
	
	    @Override
	    public CharSequence getPageTitle(int position) {
	        return position == 0? "News" : "Warnings";
	    }
	}
	

	public static class TabListViewFragment extends Fragment {
		static final String LIST_VIEW = "ListViewID";
	
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    	ListView lv = new ListView(getActivity());
	    	int i = getArguments().getInt(LIST_VIEW);
	    	if(i == 0)
				lv.setAdapter(mNewsAdapter);
			else
				lv.setAdapter(mWarningsAdapter);
	    	
	    	return lv;
	    }
	}

}
