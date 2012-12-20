package main.newstrategy.simple.ag.util.estrategias;

import logic.signedFormulas.SignedFormula;

/**
 * Seleciona fórmula de maior complexidade da lista PBCandidateList
 * @author Emerson Shigueo Sugimoto
 * */
public class AGElitistaMaiorComplexidade extends AGEstrategia {

	/**
	 * @return a SignedFormula de maior valor de fitness,<br />
	 * no caso, de maior número de conectivos
	 * @author Emerson Shigueo Sugimoto
	 * */
	@Override
	public SignedFormula getSignedFormula() {
		SignedFormula rt = getSignedFormulaMaiorComplexidade(); //evita a re-seleção
		getListaFormulasJaSelecionadas().add(rt);
		return rt;
	}

	/**
	 * @return a fórmula de maior complexidade da lista PBCandidateList
	 * */
	public SignedFormula getSignedFormulaMaiorComplexidade(){
		SignedFormula rt = null;
		int i = 0; SignedFormula posSig;
		for(i = 0; i < getPblist().size(); ++i){
			posSig = getPblist().get(i);
			if (getListaFormulasJaSelecionadas().contains(posSig)) {continue;} //já selecionado
			
			if (rt == null) {
				rt = posSig;
			} else {
				if (rt.getComplexity() < posSig.getComplexity()){
					rt = posSig; //update
				}
			}
		}
		return rt;
	}
	
}

/*
-------------------
backup:
-------------------
		//SignedFormula rt = null;
		return getAgUtil().getSignedFormulaMaiorNumeroConectivos(selecionados, getPblist());
//		//return  getAgUtil().getSignedFormulaMaiorComplexidade(selecionados, getPblist());
//		SignedFormula rt = getAgUtil().getSignedFormulaMaiorComplexidade(selecionados, getPblist());
//		if (rt==null){return null;}
//		//verifica se existe outra fórmula com a mesma complexidade
//		_listaMesmaComplexidade = getAgUtil().getListaMesmaComplexidade(rt, getPblist());
//		if (_listaMesmaComplexidade==null || _listaMesmaComplexidade.size() <= 0) {return rt;}
		
////		//existem fórmulas com a mesma complexidade....
//		//prioridade a formulas com os conectivos ->, /\,V
////		rt = getAgUtil().getSFPrioridadeConectivos(_listaMesmaComplexidade);
////		_listaMesmaComplexidade = getAgUtil().getListaMesmaComplexidade(rt, getPblist());
////		if (_listaMesmaComplexidade==null || _listaMesmaComplexidade.size() <= 0) {return rt;}
//		//return rt;
//		
//		//prioriza conectivo 'ou'
//		rt = getAgUtil().getSFPrioridadeConectivoOu(_listaMesmaComplexidade);
//		_listaMesmaComplexidade = getAgUtil().getListaMesmaComplexidade(rt, getPblist());
//		if (_listaMesmaComplexidade==null || _listaMesmaComplexidade.size() <= 0) {return rt;}
//		
		//listaMesmaComplexidade = getAgUtil().getListaMesmaComplexidade(rt, getPblist());
		
		
		//getStrategy().getProofTree().getSignedFormulasToNodes()
		
		//System.out.println("Existem items com a mesma complexidade .... ");
		
		//------------------------ Análise de Átomos Grosseira ----------------
//		AnaliseNumeroAtomosSignedGrosseiro anasg = new AnaliseNumeroAtomosSignedGrosseiro();
//		rt = anasg.GetSignedFormulaInconsitenciasSignedGrosseiro(getPblist().getListSignedFormula(), 
//				getStrategy().getProofTree().getSignedFormulasToNodes());
//		if (rt!=null){return rt;}
		
		//------------------------ Análise de Átomos -----------------
//		AnaliseNumeroAtomos ana = new AnaliseNumeroAtomos();
//		//System.out.println("--------- getPblist().getListSignedFormula(): " + getPblist().getListSignedFormula());
//		//System.out.println("------ getStrategy().getProofTree().getSignedFormulasToNodes(): " + getStrategy().getProofTree().getSignedFormulasToNodes());
//		
//		rt = ana.getSFAnaliseAtomos(getPblist().getListSignedFormula(), 
//				getStrategy().getProofTree().getSignedFormulasToNodes(), 
//				false, false);
//		//System.out.println("-----------------rt: " + (rt==null ? "NULL" : rt));
//		if (rt!=null){return rt;}
//	
//		
//		//Temporário
//		//retorna com base na seleção estocástica
//		setAgEstocastico(new AGEstocastico(_listaMesmaComplexidade));
//		rt = getAgEstocastico().GetIndividuoRoleta();
//		while(selecionados.contains(rt)){ //já usado
//			rt = getAgEstocastico().GetIndividuoRoleta();
//		}
//		setAgEstocastico(null);
//		return rt;
*/