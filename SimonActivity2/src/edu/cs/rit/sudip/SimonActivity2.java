package edu.cs.rit.sudip;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class SimonActivity2 extends Activity {

	/** serious hack: content view resource. */
	protected int myContentView = R.layout.main2;

	/** note pads and output area. */
	protected TextView green, red, yellow, blue, output;
	
	protected Animation scale; 
	
	protected MediaPlayer redSound,greenSound,blueSound,yellowSound;

	/** (re)start button. */
	protected Button start;

	/** the model. */
	protected Model model;

	/** the observer. */
	protected Logger logger;
	
	protected Runner runner;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(myContentView);

		// reference the GUI
		green = (TextView)findViewById(R.id.green);
		red = (TextView)findViewById(R.id.red);
		yellow = (TextView)findViewById(R.id.yellow);
		blue = (TextView)findViewById(R.id.blue);
		output = (TextView)findViewById(R.id.output);
		start = (Button)findViewById(R.id.start);
		
		redSound=MediaPlayer.create(this, R.raw.red);
		greenSound=MediaPlayer.create(this, R.raw.green);
		blueSound=MediaPlayer.create(this, R.raw.blue);
		yellowSound=MediaPlayer.create(this, R.raw.yellow);
		scale=AnimationUtils.loadAnimation(this, R.anim.scale);

		// create the model
		model = new Model();
		model.setObserver(logger = new Logger(this),runner = new Runner(this));

		// attach the note pad controller
		OnClickListener tones = new OnClickListener() {          
			public void onClick (View v) {
				int resource = 0;
				if (v.getId()== R.id.green){
				resource=0;	
				Log.d("Simon", "clicked note "+resource);
				model.listen(resource);
				}
				if (v.getId()== R.id.red){
					resource=1;	
					Log.d("Simon", "clicked note "+resource);
					model.listen(resource);
					}
				if (v.getId()== R.id.yellow){
					resource=2;	
					Log.d("Simon", "clicked note "+resource);
					model.listen(resource);
					}
				if (v.getId()== R.id.blue){
					resource=3;	
					Log.d("Simon", "clicked note "+resource);
					model.listen(resource);
					}
			}
		};
		green.setOnClickListener(tones);
		red.setOnClickListener(tones);
		yellow.setOnClickListener(tones);
		blue.setOnClickListener(tones);

		// attach the start button controller 
		start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d("Simon", "clicked start");
				model.start();
			}
		});
	}
	
	 protected void onPause(){
		super.onPause();
		greenSound.release();
		redSound.release();
		yellowSound.release();
		blueSound.release();
	}
	 
	 protected void onResume(){
		 super.onResume();
		 redSound=MediaPlayer.create(this, R.raw.red);
		 greenSound=MediaPlayer.create(this, R.raw.green);
		 blueSound=MediaPlayer.create(this, R.raw.blue);
		 yellowSound=MediaPlayer.create(this, R.raw.yellow);
	 }

}