package edu.cs.rit.sudip;

import java.util.ArrayList;
import java.util.Queue;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

public class Runner extends Logger implements Model.Observer{

	
	protected MediaPlayer mp;
	
	protected ArrayList<Runnable> queue = new ArrayList<Runnable>();
	
	public Runner(SimonActivity2 gui) {
		super(gui);
		// TODO Auto-generated constructor stub
		
	}

	public Runnable play (final int resource) {
		  
		
	    return new Runnable() {
	    public void run(){
	    	//MediaPlayer mp= new MediaPlayer();
	    	TextView view;
	    	Animation animation = AnimationUtils.loadAnimation(gui, R.anim.scale);
	    	//mp initialized based on value of resource
	    	if (resource == 0)
	    	{
	    		 mp=gui.greenSound;
	    		 view = gui.green;
	    	}
	    	else if (resource == 1)
	    	{
	    		 mp=gui.redSound;
	    		 view = gui.red;
	    	}
	    	else if (resource == 2)
	    	{
	    		 mp=gui.yellowSound;
	    		 view = gui.yellow;
	    	}
	    	else
	    	{
	    		 mp=gui.blueSound;
	    		 view = gui.blue;
	    	}
	    	view.startAnimation(animation);
	    	animation.setAnimationListener(new AnimationListener() {
				
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
					mp.start();
				}
				
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					mp.pause();
					mp.seekTo(0);
					
					continuation();
					
				}
			});
	    	
	    	
	    }
	    };
		  
	  }
	
	protected void continuation(){
		synchronized(queue){
			if (queue.isEmpty()==false){
				
				Runnable cmd = queue.remove(0);
				if (queue.size()!=0){
					queue.get(0).run();
				}
				
			}
			
		}
	}
	
	public void execute (Runnable... command) {
		synchronized(queue){
			int i = 0;
	    for (Runnable r: command){
	    	  
	      queue.add(r);
	      i++;
	    }
	    if (queue.size()== i && !queue.isEmpty()){
	    	queue.get(0).run();
	    }
	  }
	}
	
}
