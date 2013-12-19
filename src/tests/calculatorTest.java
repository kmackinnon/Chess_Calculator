package tests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import calculate.Calculator;

/**
 * 
 * Used to ensure that each component of the ratings calculator works for boundary and average inputs.
 * 
 * @author Keith MacKinnon
 * @date Dec 18, 2013
 * 
 */
public class calculatorTest {

	Calculator calc;
	
	@Before
	public void setUp() throws Exception {
		calc = Calculator.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testComputeRatingDiff() {
		assertTrue("Ensure 400 point max gap.", calc.computeRatingDiff(2100, 1699) == 400);
		assertTrue("Ensure 400 point max gap.", calc.computeRatingDiff(1699, 2100) == -400);
		assertTrue(calc.computeRatingDiff(2600, 2730) == -130);
		assertTrue(calc.computeRatingDiff(2730, 2600) == 130);
	}

	@Test
	// rating difference must be between -400 to 400
	public void testComputeExpectedScore() {
		assertTrue(calc.computeExpectedScore(0) == 0.50);
		assertTrue(calc.computeExpectedScore(10) == 0.51);
		assertTrue(calc.computeExpectedScore(-10) == 0.49);
		assertTrue(calc.computeExpectedScore(400) == 0.92);
		assertTrue(calc.computeExpectedScore(-400) == 0.08);
	}

	@Test
	public void testComputeNewRating() {
		assertTrue(calc.computeNewRating(2100, 5) == 2105);
		assertTrue(calc.computeNewRating(1000, -2) == 1000);
		assertTrue(calc.computeNewRating(1000, 2) == 1002);
		assertTrue(calc.computeNewRating(2100, 0.33) == 2100.33);
	}

	@Test
	public void testComputeRatingChange() {
		assertTrue(calc.computeRatingChange(1000, 30, 1.0, 0.5) == 15);
		assertTrue("No change since rating can't drop", calc.computeRatingChange(1000, 15, 0, 0.5) == 0);
	}

	@Test
	public void testRound() {
		assertTrue(calc.round(0.333, 2) == 0.33);
		assertTrue(calc.round(0, 2) == 0.00);
	}

}
