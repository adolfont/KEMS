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
		SignedFormula sf0, sf1, sf2, sf3, sf4, sf5, sf6, sf7, sf8, sf9, sf10, sf11, sf12, sf13, sf14, sf15, sf16, sf17;

		sf0 = sfc.parseString("T !(A&!ABC)"); // invalid
		sf1 = sfc.parseString("F !(B&!B)"); //valid
		sf2 = sfc.parseString("F !(!B&!!B)");
		sf3 = sfc.parseString("F !((A&B)&!(A&B))");
		sf4 = sfc.parseString("F !((A&B)&!(A&A))"); //invalid
		sf5 = sfc.parseString("F !(((A&B)|(C&D))&!((A&B)|(C&D)))");
		sf6 = sfc.parseString("F !(((A&B)->(C&D))&!((A&B)->(C&D)))");
		sf7 = sfc.parseString("T !(((A&B)->(C&D))&!((A&B)|(C&D)))"); //invalid
		sf8 = sfc.parseString("F !((A1->!C1)&!(A1->!C1))");
		sf9 = sfc.parseString("F !((A2&!(B2->C3))&!((A2&!(B2->C3))))");
		sf10 = sfc.parseString("F !(((B2->C3)&!A2)&!(((B2->C3)&!A2)))");
		sf11 = sfc.parseString("T !(((B2&!!!C3)&!A2)&!(((B2&!!!C3)&!A2)))");
		sf12 = sfc.parseString("T !(((B2&!C3)&!(A2->C1))&!(((B2&!C3)&!(A2|C1))))"); //invalid
		sf13 = sfc.parseString("F !((!(B&!B))&!(!(B&!B)))");
		sf14 = sfc.parseString("F ((!(B&!B))&!(!(B&!B)))"); //invalid
		sf15 = sfc.parseString("F !((!(B&!B2))&!(!(B&!B1)))"); //invalid
		sf16 = sfc.parseString("T !(((B2&!C3)&!(A2->C1)->(A2&!(!!C4&!!!C3)))&!(((B2&!C3)&!(A2->C1)->(A2&!(!!C4&!!!C3)))))");
		sf17 = sfc.parseString("F !(!(!E4&!C4)&!!(!E4&!C4))");
		
		
		assertEquals(-1, ccc.compare(sf0, sf1));
		assertEquals(1, ccc.compare(sf1, sf0));
		
		assertEquals(0, ccc.compare(sf1, sf2));
		assertEquals(1, ccc.compare(sf2, sf0));
		
		assertEquals(1, ccc.compare(sf3, sf0));
		assertEquals(0, ccc.compare(sf3, sf1));
		assertEquals(-1, ccc.compare(sf0, sf3));
		assertEquals(-1, ccc.compare(sf4, sf3));
		assertEquals(1, ccc.compare(sf3, sf4));
		assertEquals(0, ccc.compare(sf0, sf4));
		
		assertEquals(-1, ccc.compare(sf0, sf5));
		assertEquals(1, ccc.compare(sf5, sf0));
		
		assertEquals(-1, ccc.compare(sf0, sf6));
		assertEquals(1, ccc.compare(sf6, sf0));
		
		assertEquals(0, ccc.compare(sf0, sf7));
		assertEquals(0, ccc.compare(sf7, sf0));
		
		assertEquals(-1, ccc.compare(sf0, sf8));
		assertEquals(1, ccc.compare(sf8, sf0));
		assertEquals(0, ccc.compare(sf6, sf8));
		assertEquals(0, ccc.compare(sf8, sf6));
		
		assertEquals(0, ccc.compare(sf8, sf9));
		assertEquals(0, ccc.compare(sf9, sf8));
		assertEquals(-1, ccc.compare(sf0, sf9));
		assertEquals(1, ccc.compare(sf9, sf0));
		
		assertEquals(0, ccc.compare(sf10, sf9));
		assertEquals(0, ccc.compare(sf9, sf10));
		assertEquals(-1, ccc.compare(sf0, sf10));
		assertEquals(1, ccc.compare(sf10, sf0));
		
		assertEquals(0, ccc.compare(sf10, sf11));
		assertEquals(0, ccc.compare(sf11, sf10));
		assertEquals(-1, ccc.compare(sf0, sf11));
		assertEquals(1, ccc.compare(sf11, sf0));
		
		assertEquals(0, ccc.compare(sf0, sf12));
		assertEquals(0, ccc.compare(sf12, sf0));
		assertEquals(0, ccc.compare(sf4, sf12));
		assertEquals(0, ccc.compare(sf12, sf4));
		assertEquals(0, ccc.compare(sf7, sf12));
		assertEquals(0, ccc.compare(sf12, sf7));
		assertEquals(-1, ccc.compare(sf12, sf11));
		assertEquals(1, ccc.compare(sf11, sf12));
		
		assertEquals(-1, ccc.compare(sf0, sf13));
		assertEquals(1, ccc.compare(sf13, sf0));
		assertEquals(0, ccc.compare(sf11, sf13));
		assertEquals(0, ccc.compare(sf13, sf11));
		
		assertEquals(0, ccc.compare(sf0, sf14));
		assertEquals(0, ccc.compare(sf14, sf0));
		assertEquals(-1, ccc.compare(sf14, sf13));
		assertEquals(1, ccc.compare(sf13, sf14));
		
		assertEquals(0, ccc.compare(sf0, sf15));
		assertEquals(0, ccc.compare(sf15, sf0));
		assertEquals(-1, ccc.compare(sf15, sf13));
		assertEquals(1, ccc.compare(sf13, sf15));
		
		assertEquals(-1, ccc.compare(sf0, sf16));
		assertEquals(1, ccc.compare(sf16, sf0));
		assertEquals(0, ccc.compare(sf16, sf13));
		assertEquals(0, ccc.compare(sf13, sf16));
		
		assertEquals(-1, ccc.compare(sf0, sf17));
		assertEquals(1, ccc.compare(sf17, sf0));
		assertEquals(0, ccc.compare(sf17, sf13));
		assertEquals(0, ccc.compare(sf13, sf17));
		
	}

}
