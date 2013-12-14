package tests;

import static org.junit.Assert.*;

import calculate.Tournament;
import calculate.Calculator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class calculatorTest {

	Tournament event;
	Calculator calc;
	int[] players = {2000, 2100, 2050};
	
	@Before
	public void setUp() throws Exception {
		event = new Tournament(2100, 1.0, 15, players);
		calc = new Calculator(event);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNewRating() {
		assertTrue(calc.computeNewRating(2100, 5) == 2105);
	}

}
