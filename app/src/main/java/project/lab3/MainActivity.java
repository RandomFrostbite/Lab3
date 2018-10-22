package project.lab3;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer backgroundPlayer;
    private MediaPlayer buttonPlayer;
    static public Uri[] sounds;
    public static final String SOUND_ID = "sound id";
    public static final int BUTTON_REQUEST = 1;
    private int current_sound = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( backgroundPlayer.isPlaying() )
                    Snackbar.make(view, "Media controls", Snackbar.LENGTH_LONG)
                        .setAction("Pause", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                    backgroundPlayer.pause(); } }).show();
                else
                    Snackbar.make(view, "Media controls", Snackbar.LENGTH_LONG)
                            .setAction("Resume", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    backgroundPlayer.start(); } }).show();
            }
        });

        // My code below
        final ImageButton imButton = findViewById(R.id.face_btn);
        imButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int random_color = 0xff000000;
                for (int i = 0; i < 3; i++)
                    random_color |= (int)(255 * Math.random()) << (8*i);
                ((ImageButton)v).setColorFilter(random_color);

                //Reset the player
                buttonPlayer.reset();
                try {
                    buttonPlayer.setDataSource(getApplicationContext(),sounds[current_sound]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Prepare the player asynchronously
                buttonPlayer.prepareAsync();
                //No need to call start() since we call with onPreparedListener
            }
        });
        imButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Create an Intent that will be used to launch the SecondActivity
                Intent soundPick = new Intent( getApplicationContext(), SecondActivity.class );
                //Attach the current_sound value to the Intent. This value can be
                //retrieved with the SOUND_ID key.
                soundPick.putExtra( SOUND_ID, current_sound );
                soundPick.putExtra("ButtonNum", 1 );
                //Start the SecondActivity indicating that it will give a result
                //back for a BUTTON_REQUEST request code
                startActivityForResult( soundPick, BUTTON_REQUEST );
                return true;
            }
        });

        final ImageButton imButton2 = findViewById(R.id.face_btn2);
        imButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int random_color = 0xff000000;
                for (int i = 0; i < 3; i++)
                    random_color |= (int)(255 * Math.random()) << (8*i);
                ((ImageButton)v).setColorFilter(random_color);

                //Reset the player
                buttonPlayer.reset();
                try {
                    buttonPlayer.setDataSource(getApplicationContext(),sounds[current_sound]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Prepare the player asynchronously
                buttonPlayer.prepareAsync();
                //No need to call start() since we call with onPreparedListener
            }
        });
        imButton2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Create an Intent that will be used to launch the SecondActivity
                Intent soundPick = new Intent( getApplicationContext(), SecondActivity.class );
                //Attach the current_sound value to the Intent. This value can be
                //retrieved with the SOUND_ID key.
                soundPick.putExtra( SOUND_ID, current_sound );
                soundPick.putExtra("ButtonNum", 2 );
                //Start the SecondActivity indicating that it will give a result
                //back for a BUTTON_REQUEST request code
                startActivityForResult( soundPick, BUTTON_REQUEST );
                return true;
            }
        });

        // Media
        sounds = new Uri[4];
        //Use parse method of the Uri class to obtain the Uri of a resource
        //specified by a string
        sounds[0] = Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.ringd);
        sounds[1] = Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.ring01);
        sounds[2] = Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.ring02);
        sounds[3] = Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.ring03);
        buttonPlayer = new MediaPlayer();
        buttonPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        buttonPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //Pause the backgroundPlayer
                backgroundPlayer.pause();
                //Start the buttonPlayer
                mp.start();
            }
        });

        buttonPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                backgroundPlayer.start();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Send the background player to the paused state
        backgroundPlayer.pause();
        buttonPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Create and prepare MediaPlayer with R.raw.mario as the data stream
        //source
        backgroundPlayer = MediaPlayer.create(this, R.raw.intro);
        //Define a procedure that will be executed when the MediaPlayer goes to
        //the prepared state
        backgroundPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
            //Set the looping parameter to true
            mp.setLooping(true);
            //Start the playback.
            //By placing the start method in the onPrepared event
            //we are always certain that the audio stream is prepared
            mp.start();
            }});
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Release the background player when we don’t need it.
        backgroundPlayer.release();
        //buttonPlayer.release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Make sure the request was successful
            if( requestCode == BUTTON_REQUEST )
            {
                current_sound = data.getIntExtra(SOUND_ID,0);
            }
        }
        else if( resultCode == RESULT_CANCELED ){
            Toast.makeText(getApplicationContext(),getText( R.string.back_message ),Toast.LENGTH_SHORT).show();
        }
    }

    // Not mine below
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
