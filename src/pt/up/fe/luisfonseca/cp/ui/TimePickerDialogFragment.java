package pt.up.fe.luisfonseca.cp.ui;
import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

public class TimePickerDialogFragment extends DialogFragment {

	private OnTimeSetListener mDateSetListener;

	public TimePickerDialogFragment() {
	}

	public TimePickerDialogFragment(OnTimeSetListener callback) {
		mDateSetListener = (OnTimeSetListener) callback;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Calendar cal = Calendar.getInstance();

		return new TimePickerDialog(getActivity(),
				mDateSetListener,
				cal.get(Calendar.HOUR_OF_DAY),
				0,
				DateFormat.is24HourFormat(getActivity()));
	}

}