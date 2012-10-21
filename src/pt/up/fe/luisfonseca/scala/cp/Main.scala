package pt.up.fe.luisfonseca.scala.cp
import android.os.Bundle
import android.view.View
import pt.up.fe.luisfonseca.cp.ui.R

class Main extends android.app.Activity
{
	implicit def func2OnClickListener(func: (View) => Unit) = {
        new View.OnClickListener() {
            override def onClick(v: View) = func(v)
        }
	}
	
	//lazy val textView = findViewById(R.id.text).asInstanceOf[TextView]
	//lazy val button = findViewById(R.id.button).asInstanceOf[Button]
	                
	override def onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        //button.setOnClickListener((v : View) => textView.setText("Hello Scala"))
	}
}