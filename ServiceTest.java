package festival.test;

import org.junit.Assert;
import org.junit.Test;
import festival.*;

/**
 * Basic tests for the {@link Service} implementation class.
 * 
 * A more extensive test suite will be performed for assessment of your code,
 * but this should get you started writing your own unit tests.
 */
public class ServiceTest {

	/** Test construction of a typical service. */
	@Test
	public void testTypicalService() {
		Venue source = new Venue("v1");
		Venue destination = new Venue("v2");
		int session = 2;

		Service service = new Service(source, destination, session);
		Assert.assertEquals(source, service.getSource());
		Assert.assertEquals(destination, service.getDestination());
		Assert.assertEquals(session, service.getSession());
		Assert.assertTrue("Invariant incorrect", service.checkInvariant());
		Assert.assertEquals("Departs v1 after session 2 for v2",
				service.toString());
	}

	/**
	 * Check that the appropriate exception is thrown if a service is created
	 * with an invalid session number.
	 */
	@Test(expected = InvalidSessionException.class)
	public void testInvalidSessionException() {
		Venue source = new Venue("v1");
		Venue destination = new Venue("v2");
		int session = -2;

		Service s1 = new Service(source,destination,session);
	}

	/**
	 * Test that the appropriate exception is thrown when a service is created
	 * with equal source and destination stations.
	 */
	@Test(expected = InvalidServiceException.class)
	public void testSameSourceDestination() {
		Service s1 = new Service(new Venue("v1"), new Venue("v1"), 2);
	}

	@Test(expected = NullPointerException.class)
	public void testNullSource() {
		Service s1 = new Service(null, new Venue("v1"),2);
	}

	@Test(expected = NullPointerException.class)
	public void testNullDestination() {
		Service s1 = new Service(new Venue("v1"),null,2);
	}

	@Test
	public void testEquals() {
		Venue source = new Venue("v1");
		Venue destination = new Venue("v2");
		int session = 2;

		Service s1 = new Service(source,destination,session);
		Assert.assertTrue(s1.equals(s1));
		Service s2 = new Service(source,destination,session);
		Assert.assertTrue(s1.equals(s2));
		Service s3 = new Service(new Venue("v1"),destination,session);
		Assert.assertTrue(s1.equals(s3));
		Service s4 = new Service(source,new Venue("v2"),session);
		Assert.assertTrue(s1.equals(s4));
		Service s5 = s1;
		Assert.assertTrue(s1.equals(s5));
		Event e1 = new Event(source,2,"MusicBand");
		Assert.assertFalse(s1.equals(e1));
	}

	@Test
	public void testHashCode() {
		Venue source = new Venue("v1");
		Venue destination = new Venue("v2");
		int session = 2;

		Service s1 = new Service(source,destination,session);
		Service s2 = new Service(source,destination,session);
		Assert.assertTrue(s1.hashCode() == s2.hashCode());
	}
}
