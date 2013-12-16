package tests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import calculate.Calculator;

public class calculatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testComputeRatingDiff() {
		assertTrue("Ensure 400 point max gap.", Calculator.computeRatingDiff(2100, 1699) == 400);
		assertTrue("Ensure 400 point max gap.", Calculator.computeRatingDiff(1699, 2100) == -400);
		assertTrue(Calculator.computeRatingDiff(2600, 2730) == -130);
		assertTrue(Calculator.computeRatingDiff(2730, 2600) == 130);
	}

	@Test
	// rating difference must be between -400 to 400
	public void testComputeExpectedScore() {
		assertTrue(Calculator.computeExpectedScore(0) == 0.50);
		assertTrue(Calculator.computeExpectedScore(10) == 0.51);
		assertTrue(Calculator.computeExpectedScore(-10) == 0.49);
		assertTrue(Calculator.computeExpectedScore(400) == 0.92);
		assertTrue(Calculator.computeExpectedScore(-400) == 0.08);
	}

	@Test
	public void testComputeNewRating() {
		assertTrue(Calculator.computeNewRating(2100, 5) == 2105);
		assertTrue(Calculator.computeNewRating(1000, -2) == 1000);
		assertTrue(Calculator.computeNewRating(1000, 2) == 1002);
		assertTrue(Calculator.computeNewRating(2100, 0.33) == 2100.33);
	}

	@Test
	public void testComputeRatingChange() {
		assertTrue(Calculator.computeRatingChange(1000, 30, 1.0, 0.5) == 15);
		assertTrue("Rating not below 1000", Calculator.computeRatingChange(1000, 15, 0, 0.5) == 0);
	}

	@Test
	public void testCheckValidity() {
		assertTrue(Calculator.checkValidity(false, false) == false);
		assertTrue(Calculator.checkValidity(false, true) == false);
		assertTrue(Calculator.checkValidity(true, false) == false);
		assertTrue(Calculator.checkValidity(true, true) == true);
	}

	@Test
	public void testRound() {
		assertTrue(Calculator.round(0.333, 2) == 0.33);
		assertTrue(Calculator.round(0, 2) == 0.00);
	}

}
