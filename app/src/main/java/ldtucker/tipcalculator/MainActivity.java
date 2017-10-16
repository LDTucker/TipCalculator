/**
 * Main Activity
 */
package ldtucker.tipcalculator;

import android.app.Activity;    //base class
import android.os.Bundle;   //for saving state information
import android.text.Editable;   //for EditText even handling
import android.text.TextWatcher;    //EditText Listener
import android.widget.EditText;    //for bill amount input
import android.widget.SeekBar;  //for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener;  //SeekBar listener
import android.widget.TextView;   //for displaying text
import java.text.NumberFormat;  //for currency formatting

public class MainActivity extends Activity {

    //currency and percent formatter objects
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;    //bill amount entered by user

    private double percent = 0.15;  //initial tip percentage

    private TextView amountTextView;    //show formatted bill amount

    private TextView percentTextView;    //shows tip percentage

    private TextView tipTextView;   //shows calculated tip amount

    private TextView totalTextView;     //shows calculated total bill amount

    //called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     //call superclass's version
        setContentView(R.layout.activity_main);     //inflate the GUI

        //get references to programmatically manipulate TextViews
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextvView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        tipTextView.setText(currencyFormat.format(0));  //set text to 0
        totalTextView.setText(currencyFormat.format(0));    //set text to 0

        //set amountEditText's TextWatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        //set percentSeekBar OnSeekChangListener
        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    //calculate and display tip and total amounts
    private void calculate(){
        //format percent and display in percentTextView
        percentTextView.setText(percentFormat.format(percent));

        //calculate the tip and total
        double tip = billAmount * percent;
        double total = billAmount + tip;

        //display tip and total formatted as currency
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
    }

    //listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {

        //update percent, then call calculate
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            percent = i / 100.00;   //set percent based on progress
            calculate();    //calculate and display tip and total
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    //listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        //called when the user modifies the bill amount
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {   //get the bill amount and display the currency formatted value
                billAmount = Double.parseDouble(s.toString());
                amountTextView.setText(currencyFormat.format(billAmount));
            }
            catch (NumberFormatException e) {
                amountTextView.setText("");
                billAmount = 0.0;
            }

            calculate();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
