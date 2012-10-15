package pt.up.fe.luisfonseca.cp.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import pt.up.fe.luisfonseca.cp.api.ResponseHandler;
import pt.up.fe.luisfonseca.cp.api.StationsLoader;
import pt.up.fe.luisfonseca.util.ArrayAdapterStartsWith;
import scala.Enumeration.Value;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SearchFragment extends Fragment
	implements OnMenuItemClickListener, ResponseHandler<List<String>>
{
	
	AutoCompleteTextView mAutocompleteSearchFrom,
						 mAutocompleteSearchTo;
	
	TextView mDateTextView,
		     mTimeTextView;
	
	Spinner mSpinnerType;
	
	Button mSearchButton;
	
	MenuItem mGetTrains;
	
	StationsLoader mStationsLoader;
	
	
    public SearchFragment() {
    	//mStationsLoader = new StationsLoader(this);
    	//mStationsLoader.execute();
    }

    public static final String ARG_SECTION_NUMBER = "section_number";
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	View v = inflater.inflate(R.layout.fragment_search, container, false);
    	mDateTextView = (TextView) v.findViewById(R.id.tvDate);
        mDateTextView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				DialogFragment newFragment = new DatePickerDialogFragment(new OnDateSetListener() {
					
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						
						String fill_seperator = " " + getString(R.string.date_format_fill) + " ";
						Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
						mDateTextView.setText(
								new SimpleDateFormat(getString(R.string.date_format))
									.format(cal.getTime()).toString()
									.replaceAll("\\.", fill_seperator));
					}
				});
				newFragment.show(ft, "date_dialog");
			}
		});
        
    	mTimeTextView = (TextView) v.findViewById(R.id.tvTime);
    	mTimeTextView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				DialogFragment newFragment = new TimePickerDialogFragment(new OnTimeSetListener() {
					
					public void onTimeSet(TimePicker view, int hour, int minute) {
						mTimeTextView.setText((hour < 10 ?   "0" : "") + hour + ":00");
					}
				});
				newFragment.show(ft, "time_dialog");
			}
		});

    	mSpinnerType = (Spinner) v.findViewById(R.id.spinnerType);
    	
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
    			getActivity(),
    			R.array.train_type_array,
    			android.R.layout.simple_spinner_item);
    	
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	mSpinnerType.setAdapter(adapter);

    	mAutocompleteSearchFrom = (AutoCompleteTextView) v.findViewById(R.id.autocomplete_searchfrom);
    	mAutocompleteSearchTo = (AutoCompleteTextView) v.findViewById(R.id.autocomplete_searchto);
    	
        return v;
    }

	public void onError(Value error) {
		Toast.makeText(this.getActivity(), getString(R.string.toast_no_internet), Toast.LENGTH_SHORT)
		 .show();
	}

	public void onResultReceived(List<String> results) {
		ArrayAdapterStartsWith<Object> aAdapter = new ArrayAdapterStartsWith<Object>(
				getActivity(),
				android.R.layout.simple_list_item_1,
				new String[] {"porto", "lisboa"});

		mAutocompleteSearchFrom.setAdapter(aAdapter);
		mAutocompleteSearchTo.setAdapter(aAdapter);
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		//mStationsLoader.cancel(true);
	}


	public boolean onMenuItemClick(MenuItem args) {
		if (mAutocompleteSearchFrom.getText().toString().isEmpty())
		{
			Toast.makeText(this.getActivity(), getString(R.string.search_toast_from), Toast.LENGTH_SHORT)
				 .show();
			mAutocompleteSearchFrom.requestFocus();
			return false;
		}
		if (mAutocompleteSearchTo.getText().toString().isEmpty())
		{
			Toast.makeText(this.getActivity(), getString(R.string.search_toast_to), Toast.LENGTH_SHORT)
				 .show();
			mAutocompleteSearchTo.requestFocus();
			return false;
		}
		
		if (mAutocompleteSearchFrom.getText().toString()
			.equals(mAutocompleteSearchTo.getText().toString()))
		{
			Toast.makeText(this.getActivity(), getString(R.string.search_toast_different), Toast.LENGTH_SHORT)
				 .show();
			return false;
		}
		
		Intent i = new Intent(getActivity(), SearchActivity.class);
		i.putExtra("from", mAutocompleteSearchFrom.getText());
		i.putExtra("to", mAutocompleteSearchTo.getText());
		i.putExtra("date", mDateTextView.getText());
		i.putExtra("time", mTimeTextView.getText());
		i.putExtra("type", "" + mSpinnerType.getSelectedItemId());
		startActivity(i);
		return true;
	}

}
