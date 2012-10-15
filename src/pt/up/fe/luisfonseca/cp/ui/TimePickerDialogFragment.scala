package pt.up.fe.luisfonseca.cp.ui

import android.support.v4.app.DialogFragment
import android.app.TimePickerDialog.OnTimeSetListener
import android.app.TimePickerDialog
import android.text.format.DateFormat;
import android.os.Bundle
import java.util.Calendar
import android.app.Dialog

class TimePickerDialogFragment(mDateSetListener: OnTimeSetListener) extends DialogFragment {

	override def onCreateDialog(savedInstanceState: Bundle): Dialog = {
		val cal = Calendar.getInstance();

		return new TimePickerDialog(getActivity(),
				mDateSetListener,
				cal.get(Calendar.HOUR_OF_DAY),
				0,
				DateFormat.is24HourFormat(getActivity()));
	}
}