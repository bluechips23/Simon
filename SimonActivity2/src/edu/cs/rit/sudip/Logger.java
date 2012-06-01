package edu.cs.rit.sudip;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;
import java.text.MessageFormat;
import java.util.ArrayList;

/** an observer which mostly logs the commands.
 *  Uses package access to the activity.  */
public class Logger implements Model.Observer {
  /** for interaction. */
  protected final SimonActivity2 gui;
  private static final Object lock = new Object();

  int flag = 0;
  public Logger (SimonActivity2 gui) {
    this.gui = gui;
  }

  public Runnable say (final int resource, final int count) {
    return new Runnable() {
      public void run () {
        String s = MessageFormat.format(gui.getString(resource), count);
        Log.d("Simon", "say "+s);
        gui.output.setText(s);
        //continuation();
      }
    };
  }

  public Runnable set (final int resource, final int count) {
    return new Runnable() {
      public void run () {
        String s = MessageFormat.format(gui.getString(resource), count);
        Log.d("Simon", "set "+s);
        gui.start.setText(s);
        //continuation();
      }
    };
  }

  public Runnable enable (final boolean notes, final boolean start) {
    return new Runnable() {
      public void run () {
        Log.d("Simon", "enable "+notes+" "+start);
        gui.green.setEnabled(notes);
        gui.red.setEnabled(notes);
        gui.yellow.setEnabled(notes);
        gui.blue.setEnabled(notes);
        gui.start.setEnabled(start);
        //continuation();
      }
    };
  }

  public Runnable play (final int resource) {
	  
	  
    return new Runnable() {
    	MediaPlayer mp;
   
      public void run () {
        Log.d("Simon", "play "+resource);
       
      }
    };
	  
  }
 

  public void execute (Runnable... command) {
    for (Runnable r: command)
      r.run();
  }
  
  /** template method. */
  protected void continuation () { }
}