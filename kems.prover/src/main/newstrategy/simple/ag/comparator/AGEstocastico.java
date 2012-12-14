package main.newstrategy.simple.ag.comparator;

import java.util.Iterator;

import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaCreator;

/**
 * Seleciona indivíduos da lista PBCandidateList com base na roleta 
 * 
 * @author Emerson Shigueo Sugimoto
 */
public class AGEstocastico {
	private PBCandidateList _pblist;
	
	public PBCandidateList getPblist() {return _pblist;}
	public void setPblist(PBCandidateList _pblist) {this._pblist = _pblist;}

	public AGEstocastico(PBCandidateList pbList) {
		setPblist(pbList);
	}
	
	/**
	 * Usar apenas para testes
	 * @return retorna uma PBCandidateList para testes (!)
	 * */
	@Deprecated
	public static PBCandidateList GetPBCandidateListTest(){
		PBCandidateList rt = new PBCandidateList();
		SignedFormulaCreator sfc = new SignedFormulaCreator("satlfiinconsdef");
		rt.add(sfc.parseString("F(A->B)->C"));
		rt.add(sfc.parseString("T ((A & B) -> (B & C)) -> (A & C)"));
		sfc = null;
		return rt;
	}
	
	public void print(){
		if (getPblist()==null || getPblist().size() <= 0) {System.out.println("Lista nula"); return;}
		String prt = "";
		
		for (Iterator<SignedFormula> sgF = getPblist().iterator();sgF.hasNext();) {
			prt += sgF.next().toString() + "\n";
		}
		System.out.println(prt);
	}
	
	public long GetSomaAvaliacoes(){
		if (getPblist()==null || getPblist().size() <= 0) {return 0;}
		long rt = 0;
		for (Iterator<SignedFormula> sgF = getPblist().iterator();sgF.hasNext();) {
			rt += ((SignedFormula)sgF.next()).getComplexity();
		}
		return rt;
	}
	
	public int Roleta(){
		if (getPblist()==null || getPblist().size() <= 0) {return -1;}
		int i; double aux = 0;
		double limite = Math.random() * this.GetSomaAvaliacoes();
		for(i = 0; ( (i < getPblist().size()) && (aux < limite) ); ++i){
			aux += getPblist().get(i).getComplexity();
		}
		i--;
		return i;
	}
	
	public SignedFormula GetIndividuoRoleta(int pos){
		if (pos<0){return null;}
		return getPblist().get(pos);
	}
	
	public SignedFormula GetIndividuoRoleta(){
		return GetIndividuoRoleta(this.Roleta());
	}
	
	/*//código retirado de Linden, 2006. página 64.
	public int roleta(){
		int i;
		double aux = 0;
		calculaSomaAvaliacoes();
		double limite = Math.random() * this.somaAvaliacoes();
		for(i = 0; ( (i < this.populacao.size()) && (aux < limite) ); ++i){
			aux += ((ElementoGA) populacao.get(i)).getAvaliacao();
		}
		i--;
		return i;
	}*/


}