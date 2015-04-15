package festival.test;

import org.junit.Assert;
import org.junit.Test;

import festival.*;

/**
 * Basic tests for the {@link Event} implementation class.
 * 
 * Write your own tests for the class here.
 */
public class EventTest {

	/** Test construction of a typical event. */
	@Test
	public void testTypicalEvent() {
		Venue venue = new Venue("v1");
		String act = "MusicBand";
		int session = 2;

		Event event = new Event(venue,session,act);
		Assert.assertEquals(venue, event.getVenue());
		Assert.assertEquals(act, event.getAct());
		Assert.assertEquals(session, event.getSession());
		Assert.assertTrue("Invariant incorrect", event.checkInvariant());
		Assert.assertEquals("MusicBand: session 2 at v1",
				event.toString());
	}

		@Test(expected = InvalidSessionException.class)
	public void testInvalidSessionException() {
		Venue venue = new Venue("v1");
		String act = "MusicBand";
		int session = -2;

		Event event = new Event(venue,session,act);
	}

	@Test(expected = NullPointerException.class)
	public void testNullVenue() {
		Event event = new Event(null,2,"MusicBand");
	}

	@Test(expected = NullPointerException.class)
	public void testNullAct() {
		Event event = new Event(new Venue("v1"),2,null);
	}

	@Test
	public void testEquals() {
		Venue venue = new Venue("v1");
		String act = "MusicBand";
		int session = 2;

		Event e1 = new Event(venue,session,act);
		Assert.assertTrue(e1.equals(e1));
		Event e2 = new Event(venue,session,act);
		Assert.assertTrue(e1.equals(e2));
		Event e3 = new Event(new Venue("v1"),session,act);
		Assert.assertTrue(e1.equals(e3));
		Event e4 = e1;
		Assert.assertTrue(e1.equals(e4));
		Service s1 = new Service(venue,new Venue("v2"),session);
		Assert.assertFalse(e1.equals(s1));
	}

	@Test
	public void testHashCode() {
		Venue venue = new Venue("v1");
		String act = "MusicBand";
		int session = 2;

		Event e1 = new Event(venue,session,act);
		Event e2 = new Event(venue,session,act);
		Assert.assertTrue(e1.hashCode() == e2.hashCode());
	}

	@Test
	public void testCompareTo() {
		Event e1 = new Event(new Venue("a"),2,"a");
		Assert.assertEquals(e1.compareTo(e1),0);
		Event e2 = new Event(new Venue("b"),3,"b");
		Assert.assertEquals(e1.compareTo(e2),"a".compareTo("c"));
		Event e3 = new Event(new Venue("a"),2,"b");
		Assert.assertEquals(e1.compareTo(e3),"a".compareTo("b"));
		Event e4 = new Event(new Venue("a"),5,"a");
		Assert.assertEquals(e1.compareTo(e4),2 - 5);
	}
}
