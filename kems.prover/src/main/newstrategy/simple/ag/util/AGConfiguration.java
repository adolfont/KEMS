package main.newstrategy.simple.ag.util;

//import main.newstrategy.simple.ag.util.estrategias.AGElitistaConectivos;
import main.newstrategy.simple.ag.util.estrategias.AGElitistaFrequenciaAtomos;
import main.newstrategy.simple.ag.util.estrategias.AGElitistaMaiorComplexidade;
import main.newstrategy.simple.ag.util.estrategias.AGEstocasticoFrequenciaAtomos;
import main.newstrategy.simple.ag.util.estrategias.AGEstocasticoMaiorComplexidade;
import main.newstrategy.simple.ag.util.estrategias.IEstrategiaAG;
import main.newstrategy.simple.ag.util.estrategias.hibrido.AGElitistaHibridoComplexidadeFreqAtomos;

/**
 * Configuração das estratégias de AG
 * @author Emerson Shigueo Sugimoto
 * */
public class AGConfiguration {
	public static enum Abordagens {
		EstocasticoMaiorComplexidade,
		EstocasticoFrequenciaAtomos,
		
		ElitistaMaiorComplexidade,
		ElitistaFrequenciaAtomos,
		//ElitistaConectivos,
		
		ElitistaHibridoComplexidadeFreqAtomos,
		
		NotApplyAG
	}
	
	public static Abordagens GetAbordagemFromString(String abordagem){
		if (abordagem.equalsIgnoreCase("EstocasticoMaiorComplexidade")) {
			return Abordagens.EstocasticoMaiorComplexidade;
		} else if (abordagem.equalsIgnoreCase("EstocasticoFrequenciaAtomos")) {
				return Abordagens.EstocasticoFrequenciaAtomos;
		} else if (abordagem.equalsIgnoreCase("ElitistaMaiorComplexidade")) {
			return Abordagens.ElitistaMaiorComplexidade;
		} else if (abordagem.equalsIgnoreCase("ElitistaFrequenciaAtomos")) {
			return Abordagens.ElitistaFrequenciaAtomos;
//		} else if (abordagem.equalsIgnoreCase("ElitistaConectivos")) {
//			return Abordagens.ElitistaConectivos;
			
		} else if (abordagem.equalsIgnoreCase("ElitistaHibridoComplexidadeFreqAtomos")) {
			return Abordagens.ElitistaHibridoComplexidadeFreqAtomos;
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
		case EstocasticoMaiorComplexidade:
			return new AGEstocasticoMaiorComplexidade();
		case EstocasticoFrequenciaAtomos:
			return new AGEstocasticoFrequenciaAtomos();

		case ElitistaMaiorComplexidade:
			return new AGElitistaMaiorComplexidade();
		case ElitistaFrequenciaAtomos:
			return new AGElitistaFrequenciaAtomos();
//			case ElitistaConectivos:
//			return new AGElitistaConectivos();

		case ElitistaHibridoComplexidadeFreqAtomos:
			return new AGElitistaHibridoComplexidadeFreqAtomos();
			
		case NotApplyAG:
		default:
			return null;
		}
	}
	
}