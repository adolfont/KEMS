package main.proofTree.origin;

import java.util.ArrayList;
import java.util.List;

import logic.formulas.AtomicFormula;
import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logicalSystems.c1.C1Rules;
import logicalSystems.c1.C1Signs;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SignedFormulaNodeOriginTest {

	Formula f = new AtomicFormula("A");
	SignedFormulaFactory sff = new SignedFormulaFactory();
	SignedFormula x = sff.createSignedFormula(C1Signs.TRUE, f);

	SignedFormulaNode sfn;
	SignedFormulaNodeOrigin sfno;
	private List<SignedFormulaNode> sfnList;

	@Before
	public void setUp() {
		// TODO Auto-generated method stub

		sfnList = new ArrayList<SignedFormulaNode>();
		sfn = new SignedFormulaNode(x, SignedFormulaNodeState.ANALYSED, NamedOrigin.DEFINITION);

		sfnList.add(sfn);
		sfnList.add(sfn);

		sfno = new SignedFormulaNodeOrigin(C1Rules.PB, sfn, sfnList);

	}

	@Test
	public final void testToString() {
		System.out.println(sfno);
		assertTrue(sfno.toString().equals("Rule: PB Main: T A Auxiliary #1: T A Auxiliary #2: T A"));
	}

}
