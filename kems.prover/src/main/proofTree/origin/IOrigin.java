package main.proofTree.origin;

import java.util.List;

import main.proofTree.SignedFormulaNode;
import rules.IRule;

public interface IOrigin {
	
  public String getName();

	public IRule getRule();

	public SignedFormulaNode getMain();

	public List<SignedFormulaNode> getAuxiliaries();

}
