package edu.cs.rit.sudip;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

/** model for the Simon game. */
public class Model {

	/** what the observer must do. */
	public interface Observer {
		/** create a command to display a message with a count. */
		Runnable say (int resource, int count);
		/** create a command to set the start button text with a count. */
		Runnable set (int resource, int count);
		/** create a command to control interaction with notes and the start button. */
		Runnable enable (boolean notes, boolean start);
		/** create a command to play a note (might take time). */
		Runnable play (int resource);
		/** execute a list of commands. */
		void execute (Runnable... command);
	}

	/** maps 0.. to note ids which are used to encode the melody. */
	protected final int[] notes = { 0, 1, 2, 3 };

	/** provides random music. */
	protected final Random random = new Random();

	/** {@link #start()} should start with a new melody. */
	public static final int START_OVER = 0;

	/** {@link #start()} should extend and play the melody. */
	public static final int START = 1;

	/** the current state of {@link #start()}. */
	protected int state = START_OVER;

	protected boolean flag = false;
	/** contains a melody. */
	protected ArrayList<Integer> melody;

	/** next note which user must send to {@link #listen(int)}. */
	protected int nextNote = -1;

	/** user interface. */
	protected Observer observer;
	
	protected Runner actualObserver;

	/** set observer. */
	public void setObserver (Observer observer, Runner runner) {
		this.observer = observer;
		this.actualObserver=runner;
	}

	/** client presses start button.
	 *  Depending on {@link #state} this will start with a new melody.
	 *  In any case it will add another note, play melody, and start listening.
	 */
	public void start () {
		// start with a new melody?
		if (state == START_OVER)
			melody = new ArrayList<Integer>();
		// disable user interface
		observer.execute(
				observer.set(R.string.blank, 0),
				observer.enable(false, false)
		);
		// extend the melody
		melody.add(notes[random.nextInt(notes.length)]);
		// play the melody
		for (int i = 0; i < melody.size(); ++ i)
		{
			observer.execute(
					observer.say(R.string.listen, i+1),
					observer.play(melody.get(i))
					
					);
			actualObserver.execute(
				actualObserver.play(melody.get(i))
			);
			
		
		}
		
		
		
		// client can now resign (start over) or play the first note
		state = START_OVER;
		nextNote = 0;
		observer.execute(
				observer.say(R.string.play, 1),
				observer.set(R.string.resign, 0),
				observer.enable(true, true)
		);
		
	}

	/** client plays a note. */
	public void listen (int resource) {
		// ok to accept this click?
		if (nextNote >= 0) {
			// disable all and play the click
			observer.execute(
					observer.say(R.string.blank, 0),
					observer.enable(false, false),
					observer.play(resource)
					
			);
			
			actualObserver.execute(
					actualObserver.play(resource)
				);
			// click does not match the melody?
			if (resource != melody.get(nextNote)) {
				// client can only start over
				state = START_OVER;
				observer.execute(
						observer.say(R.string.fail, nextNote+1),
						observer.set(R.string.start, 0),
						observer.enable(false, true)
				);
			// last and correct click for the melody?
			} else if (++ nextNote >= melody.size()) {
				// client can only extend the melody
				state = START;
				observer.execute(
						observer.say(R.string.success, nextNote),
						observer.set(R.string.more, nextNote+1),
						observer.enable(false, true)
				);
			// correct but not last click for the melody
			} else {
				// client can now resign (start over) or play the next note
				state = START_OVER;
				observer.execute(
						observer.say(R.string.play, nextNote+1),
						observer.enable(true, true)
				);
			}
		}
	}
}
