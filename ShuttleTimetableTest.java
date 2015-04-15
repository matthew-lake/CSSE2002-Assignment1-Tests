package festival.test;

import festival.*;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link ShuttleTimetable} implementation class.
 * 
 * A more extensive test suite will be performed for assessment of your code,
 * but this should get you started writing your own unit tests.
 */
public class ShuttleTimetableTest {

	/** Test the initial state of the shuttle timetable **/
	@Test
	public void testInitialState() {
		// the timetable under test
		ShuttleTimetable timetable = new ShuttleTimetable();

		// test that the timetable doesn't contain an arbitrary service
		Assert.assertFalse(timetable.hasService(new Service(new Venue(
				"The Arena"), new Venue("House of Noise"), 3)));

		// test that the getDestinations method returns an empty set for some
		// arbitrary venue and session number
		Set<Venue> expectedVenues = new HashSet<>();
		Assert.assertEquals(expectedVenues,
				timetable.getDestinations(new Venue("The Arena"), 3));

		// test iterator method
		Assert.assertFalse(timetable.iterator().hasNext());

		// check invariant holds
		Assert.assertTrue("Invariant failed", timetable.checkInvariant());
	}

	/** Test adding a null service to the shuttle timetable **/
	@Test(expected = NullPointerException.class)
	public void testNullAddition() {
		// the timetable under test
		ShuttleTimetable timetable = new ShuttleTimetable();
		timetable.addService(null);
	}

	/**
	 * Test adding multiple typical services (including a duplicate) to the
	 * line-up.
	 */
	@Test
	public void testMultipleServiceAdditions() {

		// some venues, sessions and shuttle services to be used in testing
		Venue[] venues =
				{ new Venue("House of Noise"), new Venue("Arena"),
						new Venue("The Corner"), new Venue("Hell") };

		int[] sessions = { 1, 2, 5 };
		Service[] services =
				{ new Service(venues[0], venues[1], sessions[0]),
					new Service(venues[0], venues[2], sessions[0]),
					new Service(venues[0], venues[1], sessions[1]),
					new Service(venues[2], venues[3], sessions[0]),
					new Service(venues[1], venues[3], sessions[1]),
					new Service(venues[1], venues[0], sessions[1]),
					new Service(venues[1], venues[3], sessions[1]) }; // duplicate

		// the timetable under test
		ShuttleTimetable timetable = new ShuttleTimetable();
		for (Service s : services) {
			timetable.addService(s);
		}

		// check that the timetable has all of the services
		for (Service s : services) {
			Assert.assertTrue(timetable.hasService(s));
		}

		// check the getDestination method where there is no destination
		Set<Venue> expectedVenues = new HashSet<>();
		Assert.assertEquals(expectedVenues,
				timetable.getDestinations(venues[0], sessions[2]));

		// check the getDestinations method where there is one destination
		expectedVenues = new HashSet<>();
		expectedVenues.add(venues[3]);
		Assert.assertEquals(expectedVenues,
				timetable.getDestinations(venues[2], sessions[0]));

		// check the getDestinations method where there are many destinations
		expectedVenues = new HashSet<>();
		expectedVenues.add(venues[3]);
		expectedVenues.add(venues[0]);
		Assert.assertEquals(expectedVenues,
				timetable.getDestinations(venues[1], sessions[1]));

		// test iterator method
		Set<Service> actualServices = new HashSet<>();
		for (Service s : timetable) {
			Assert.assertFalse("Duplicate service detected",
					actualServices.contains(s));
			actualServices.add(s);
		}
		Assert.assertEquals(new HashSet<Service>(Arrays.asList(services)),
				actualServices);

		// check invariant holds
		Assert.assertTrue("Invariant failed", timetable.checkInvariant());
	}

	@Test(expected = Exception.class)
	public void testIteratorPrivacy() { //Ensure we can't remove elements using the iterator's remove function
		// some venues, sessions and shuttle services to be used in testing
		Venue[] venues =
				{ new Venue("House of Noise"), new Venue("Arena"),
						new Venue("The Corner"), new Venue("Hell") };

		int[] sessions = { 1, 2, 5 };
		Service[] services =
				{ new Service(venues[0], venues[1], sessions[0]),
						new Service(venues[0], venues[2], sessions[0]),
						new Service(venues[0], venues[1], sessions[1]),
						new Service(venues[2], venues[3], sessions[0]),
						new Service(venues[1], venues[3], sessions[1]),
						new Service(venues[1], venues[0], sessions[1]),
						new Service(venues[1], venues[3], sessions[1]) }; // duplicate

		// the timetable under test
		ShuttleTimetable timetable = new ShuttleTimetable();
		for (Service s : services) {
			timetable.addService(s);
		}

		timetable.iterator().next();
		timetable.iterator().remove();
	}
}
