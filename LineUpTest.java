package festival.test;

import festival.*;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link LineUp} implementation class.
 * 
 * A more extensive test suite will be performed for assessment of your code,
 * but this should get you started writing your own unit tests.
 */
public class LineUpTest {

	// Correct line separator for executing machine
	private static String LINE_SEPARATOR = System.getProperty("line.separator");

	/** Test the initial state of the line-up */
	@Test
	public void testInitialState() {
		// the line-up under test
		LineUp lineUp = new LineUp();
		// empty list of events
		List<Event> expectedEvents = new ArrayList<>();
		// empty set of venues
		Set<Venue> expectedVenues = new HashSet<>();

		Assert.assertEquals(expectedEvents, lineUp.getEvents(new Venue("v1")));
		Assert.assertEquals(expectedEvents, lineUp.getEvents(3));
		Assert.assertEquals(expectedVenues, lineUp.getVenues());
		Assert.assertFalse(lineUp.iterator().hasNext());
		Assert.assertEquals(0, lineUp.getFirstUsedSession());
		Assert.assertEquals(0, lineUp.getLastUsedSession());
		Assert.assertEquals("", lineUp.toString());
		Assert.assertTrue("Invariant failed", lineUp.checkInvariant());
	}

	/** Test adding a null event to the line-up **/
	@Test(expected = NullPointerException.class)
	public void testNullAddition() {
		LineUp lineUp = new LineUp();
		lineUp.addEvent(null);
	}

	/** Test adding an event to the line-up that clashes with another event **/
	@Test(expected = InvalidLineUpException.class)
	public void testEventClash() {
		// some venues, acts and sessions and events to be used in testing
		Venue[] venues = { new Venue("House of Noise"), new Venue("Arena") };
		String[] acts = { "Def Leppard", "Behemoth", "Megadeth" };
		int[] sessions = { 8, 4, 5 };
		// events such that event[0] and event[1] clash
		Event[] events =
				{ new Event(venues[0], sessions[0], acts[0]),
						new Event(venues[1], sessions[2], acts[1]),
						new Event(venues[0], sessions[0], acts[2]) };

		// the line-up under test constructed by adding all of the events above
		LineUp lineUp = new LineUp();
		for (Event e : events) {
			lineUp.addEvent(e);
		}
	}

	/** Test adding multiple (non-clashing) events to the line-up */
	@Test
	public void testMultipleTypicalAdditions() {
		// some venues, acts and sessions and events to be used in testing
		Venue[] venues =
				{ new Venue("House of Noise"), new Venue("Arena"),
						new Venue("The Corner") };
		String[] acts = { "Def Leppard", "Behemoth", "Megadeth", "Hammerfall" };
		int[] sessions = { 8, 4, 5 };
		Event[] events =
				{ new Event(venues[1], sessions[0], acts[0]),
						new Event(venues[0], sessions[1], acts[1]),
						new Event(venues[1], sessions[1], acts[2]),
						new Event(venues[1], sessions[2], acts[3]) };

		// the line-up under test constructed by adding all of the events above
		LineUp lineUp = new LineUp();
		for (Event e : events) {
			lineUp.addEvent(e);
		}

		// the expected events for venue[1]
		List<Event> expectedEventsByVenue = new ArrayList<>();
		expectedEventsByVenue.add(events[2]);
		expectedEventsByVenue.add(events[3]);
		expectedEventsByVenue.add(events[0]);

		Assert.assertEquals(expectedEventsByVenue, lineUp.getEvents(venues[1]));

		// the expected events for session[1]
		List<Event> expectedEventsBySession = new ArrayList<>();
		expectedEventsBySession.add(events[2]);
		expectedEventsBySession.add(events[1]);

		Assert.assertEquals(expectedEventsBySession,
				lineUp.getEvents(sessions[1]));

		// the expected venues of the line-up
		Set<Venue> expectedVenues = new HashSet<>();
		expectedVenues.add(venues[0]);
		expectedVenues.add(venues[1]);

		Assert.assertEquals(expectedVenues, lineUp.getVenues());

		// expected string representation
		String expectedString =
				"Megadeth: session 4 at Arena" + LINE_SEPARATOR
						+ "Hammerfall: session 5 at Arena" + LINE_SEPARATOR
						+ "Def Leppard: session 8 at Arena" + LINE_SEPARATOR
						+ "Behemoth: session 4 at House of Noise";

		Assert.assertEquals(expectedString, lineUp.toString());

		// check the iterator of the line-up
		Set<Event> actualEvents = new HashSet<>();
		for (Event e : lineUp) {
			Assert.assertFalse("Duplicate event detected",
					actualEvents.contains(e));
			actualEvents.add(e);
		}
		Assert.assertEquals(new HashSet<Event>(Arrays.asList(events)),
				actualEvents);

		// check first and last session of the line-up, and invariant
		Assert.assertEquals(4, lineUp.getFirstUsedSession());
		Assert.assertEquals(8, lineUp.getLastUsedSession());
		Assert.assertTrue("Invariant failed", lineUp.checkInvariant());
	}

}
