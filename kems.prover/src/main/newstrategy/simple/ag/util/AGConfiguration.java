package main.newstrategy.simple.ag.util;

import main.newstrategy.simple.ag.util.estrategias.AGElitistaConectivos;
import main.newstrategy.simple.ag.util.estrategias.AGElitistaFrequenciaAtomos;
import main.newstrategy.simple.ag.util.estrategias.AGElitistaMaiorComplexidade;
import main.newstrategy.simple.ag.util.estrategias.AGEstocasticoSimple;
import main.newstrategy.simple.ag.util.estrategias.IEstrategiaAG;

/**
 * Configuração das estratégias de AG
 * @author Emerson Shigueo Sugimoto
 * */
public class AGConfiguration {
	public static enum Abordagens {
		Estocastica,
		ElitistaMaiorComplexidade,
		ElitistaConectivos,
		ElitistaFrequenciaAtomos,
		
		NotApplyAG
	}
	
	public static Abordagens GetAbordagemFromString(String abordagem){
		if (abordagem.equalsIgnoreCase("Estocastica")) {
			return Abordagens.Estocastica;
		} else if (abordagem.equalsIgnoreCase("ElitistaMaiorComplexidade")) {
			return Abordagens.ElitistaMaiorComplexidade;
		} else if (abordagem.equalsIgnoreCase("ElitistaConectivos")) {
			return Abordagens.ElitistaConectivos;
		} else if (abordagem.equalsIgnoreCase("ElitistaFrequenciaAtomos")) {
			return Abordagens.ElitistaFrequenciaAtomos;
		}
		
		return Abordagens.NotApplyAG;
	}
	
	public static IEstrategiaAG GetEstrategiaAG(String abordagem){
		return GetEstrategiaAG(GetAbordagemFromString(abordagem));
	}
	
	/**
	 * @return Retorna uma estratégia de AG baseada no enum
	 * @author Emerson Shigueo Sugimoto
	 * */
	public static IEstrategiaAG GetEstrategiaAG(Abordagens abordagem){
		switch (abordagem) {
		case ElitistaMaiorComplexidade:
			return new AGElitistaMaiorComplexidade();
		case Estocastica:
			return new AGEstocasticoSimple();
		case ElitistaConectivos:
			return new AGElitistaConectivos();
		case ElitistaFrequenciaAtomos:
			return new AGElitistaFrequenciaAtomos();
		case NotApplyAG:
		default:
			return null;
		}
	}
	
}