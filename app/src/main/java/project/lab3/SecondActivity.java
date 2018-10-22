package project.lab3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private int selected_sound = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Receive the intent
        Intent received_intent = getIntent();
        //Get the data from the Intent.
        //We use Integer class of Java for later toString() conversion
        Integer sound_id = received_intent.getIntExtra(MainActivity.SOUND_ID,0);
        //Display the current sound_id in the current_sound_text TextView
        TextView txV = (TextView)findViewById(R.id.current_sound_text);
        txV.setText( getText(R.string.current_sound_str) + " " + ((Integer)(sound_id+1)).toString() );

        String radiochecked = "sound" + ++sound_id;
        RadioButton btnchkd = findViewById( getResources().getIdentifier( radiochecked, "id", getPackageName() ) );
        btnchkd.setChecked(true);
        if ( received_intent.getIntExtra( "ButtonNum", 1 ) == 2 )
            btnchkd.setEnabled(false);
    }

    public void setSoundClick(View v){
        //Create an empty Intent and add the selected_sound variable to it
        Intent data = new Intent();
        data.putExtra( MainActivity.SOUND_ID, selected_sound );
        //Set the result code for the MainActivity and attach the data Intent
        setResult( RESULT_OK, data );
        //Destroy this Activity and propagate the ActivityResult
        finish();
    }

    //All onClick methods have to be public, return void and take View as
    //an argument
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        if ( ((RadioButton) view).isChecked() ) {
            // Check which radio button was clicked
            RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
            switch ( view.getId() ){
                case R.id.sound1: selected_sound = 0;break;
                case R.id.sound2: selected_sound = 1;break;
                case R.id.sound3: selected_sound = 2;break;
                case R.id.sound4: selected_sound = 3;break;
            }
        }
    }



}
