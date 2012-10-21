package pt.up.fe.luisfonseca.cp.ui

import android.app.DatePickerDialog.OnDateSetListener
import android.support.v4.app.DialogFragment
import java.util.Calendar
import android.app.DatePickerDialog
import android.os.Bundle
import android.app.Dialog

class DatePickerDialogFragment(mDateSetListener: OnDateSetListener) extends DialogFragment {

	override def onCreateDialog(savedInstanceState: Bundle) : Dialog = {
		val cal = Calendar.getInstance()

		return new DatePickerDialog(getActivity(),
				mDateSetListener, cal.get(Calendar.YEAR), 
				cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
	}
}