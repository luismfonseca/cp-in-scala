package pt.up.fe.luisfonseca.cp.ui

import java.text.SimpleDateFormat
import java.util.GregorianCalendar

import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.OnMenuItemClickListener
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import pt.up.fe.luisfonseca.cp.api.ResponseHandler
import pt.up.fe.luisfonseca.cp.api.StationsLoader
import pt.up.fe.luisfonseca.util.ArrayAdapterStartsWith

class SearchFragment
	extends Fragment
	with OnMenuItemClickListener
	with ResponseHandler[List[String]]
{
	
	var mAutocompleteSearchFrom : AutoCompleteTextView = null
	var mAutocompleteSearchTo : AutoCompleteTextView = null
	
	var mDateTextView: TextView = null
	var mTimeTextView: TextView = null
	
	var mSpinnerType: Spinner = null
	
	var mSearchButton: Button = null
	
	var mGetTrains: MenuItem = null
	
	var mStationsLoader: StationsLoader = null

    val ARG_SECTION_NUMBER = "section_number";
    
    override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {

    	val v = inflater.inflate(R.layout.fragment_search, container, false);
    	mDateTextView = v.findViewById(R.id.tvDate).asInstanceOf[TextView];
        mDateTextView.setOnClickListener(new View.OnClickListener() {

			def onClick(v: View) {
				val ft = getFragmentManager().beginTransaction();
				val newFragment = new DatePickerDialogFragment(new OnDateSetListener() {
					
					def onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
						
						val fill_seperator = " " + getString(R.string.date_format_fill) + " ";
						val cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
						mDateTextView.setText(
								new SimpleDateFormat(getString(R.string.date_format))
									.format(cal.getTime()).toString()
									.replaceAll("\\.", fill_seperator));
					}
				});
				newFragment.show(ft, "date_dialog");
			}
		});
        
    	mTimeTextView = v.findViewById(R.id.tvTime).asInstanceOf[TextView];
    	mTimeTextView.setOnClickListener(new View.OnClickListener() {

			def onClick(v: View) {
				val ft = getFragmentManager.beginTransaction
				val newFragment = new TimePickerDialogFragment(new OnTimeSetListener() {
					
					def onTimeSet(view: TimePicker, hour: Int, minute: Int) =
						mTimeTextView.setText(((if (hour < 10) "0" else "") + hour) + ":00")
					
				})
				newFragment.show(ft, "time_dialog");
			}
		});

    	mSpinnerType = v.findViewById(R.id.spinnerType).asInstanceOf[Spinner];
    	
    	val adapter = ArrayAdapter.createFromResource(
    			getActivity(),
    			R.array.train_type_array,
    			android.R.layout.simple_spinner_item);
    	
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	mSpinnerType.setAdapter(adapter);

    	mAutocompleteSearchFrom = v.findViewById(R.id.autocomplete_searchfrom).asInstanceOf[AutoCompleteTextView];
    	mAutocompleteSearchTo = v.findViewById(R.id.autocomplete_searchto).asInstanceOf[AutoCompleteTextView];
    	
    	setHasOptionsMenu(true);
    	
    	mStationsLoader = new StationsLoader(this)
    	mStationsLoader.execute
        v
    }

	def onError(error: ResponseHandler.Error.Type) {
		Toast.makeText(this.getActivity(), getString(R.string.toast_no_internet), Toast.LENGTH_SHORT)
		 .show();
	}

	def onResultReceived(results: List[String]) {
		val aAdapter = new ArrayAdapterStartsWith[String](
				getActivity(),
				android.R.layout.simple_list_item_1,
				results.toArray[String])

		mAutocompleteSearchFrom.setAdapter(aAdapter);
		mAutocompleteSearchTo.setAdapter(aAdapter)
	}
	
	override def onStop()
	{
		super.onStop();
		mStationsLoader.cancel(true);
	}


	override def onMenuItemClick(args: MenuItem): Boolean = {
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
		
		val i = new Intent(getActivity(), classOf[SearchActivity]);
		i.putExtra("from", mAutocompleteSearchFrom.getText());
		i.putExtra("to", mAutocompleteSearchTo.getText());
		i.putExtra("date", mDateTextView.getText());
		i.putExtra("time", mTimeTextView.getText());
		i.putExtra("type", "" + mSpinnerType.getSelectedItemId());
		startActivity(i);
		true;
	}

    override def onPrepareOptionsMenu(menu: Menu) {
    	val mi = menu.findItem(R.id.buttonSearch);
    	if(mi == null)
    		return;
    	
    	mi.setVisible(true);
        mi.setOnMenuItemClickListener(this);
    }

}