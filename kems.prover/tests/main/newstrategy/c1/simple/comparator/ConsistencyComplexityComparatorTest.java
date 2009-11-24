package main.newstrategy.c1.simple.comparator;

import static org.junit.Assert.assertEquals;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaCreator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsistencyComplexityComparatorTest {

	private ConsistencyComplexityComparator ccc;

	private SignedFormulaCreator sfc;

	@Before
	public void setUp() throws Exception {
		sfc = new SignedFormulaCreator("satlfiinconsdef");
		ccc = new ConsistencyComplexityComparator();
	}

	@After
	public void tearDown() throws Exception {
		ccc = null;
	}

	@Test
	public void testCompareSimple() {
		SignedFormula sf0, sf1, sf2;

		sf0 = sfc.parseString("T !(A&!A)");
		sf1 = sfc.parseString("F !(B&!B)");
		sf2 = sfc.parseString("T A");

		assertEquals(0, ccc.compare(sf0, sf1));
		assertEquals(0, ccc.compare(sf1, sf0));

		assertEquals(1, ccc.compare(sf0, sf2));
		assertEquals(-1, ccc.compare(sf2, sf0));

		assertEquals(1, ccc.compare(sf1, sf2));
		assertEquals(-1, ccc.compare(sf2, sf1));

		assertEquals(0, ccc.compare(sf0, sf0));
		assertEquals(0, ccc.compare(sf1, sf1));
		assertEquals(0, ccc.compare(sf2, sf2));

	}

	@Test
	public void testCompare_II() {
		SignedFormula sf0, sf1, sf2;

		sf0 = sfc.parseString("T !(ABC&!ABC)");
		sf1 = sfc.parseString("F !(B&!B)");
		sf2 = sfc.parseString("T A");

		assertEquals(0, ccc.compare(sf0, sf1));
		assertEquals(0, ccc.compare(sf1, sf0));

		assertEquals(1, ccc.compare(sf0, sf2));
		assertEquals(-1, ccc.compare(sf2, sf0));

		assertEquals(1, ccc.compare(sf1, sf2));
		assertEquals(-1, ccc.compare(sf2, sf1));

		assertEquals(0, ccc.compare(sf0, sf0));
		assertEquals(0, ccc.compare(sf1, sf1));
		assertEquals(0, ccc.compare(sf2, sf2));

	}

	@Test
	public void testCompare_III() {
		SignedFormula sf0, sf1;

		sf0 = sfc.parseString("T !(A&!ABC)");
		sf1 = sfc.parseString("F !(B&!B)");

		assertEquals(-1, ccc.compare(sf0, sf1));
		assertEquals(1, ccc.compare(sf1, sf0));

	}

}
