package main.ag.strategy.util;

//import main.ag.strategy.AGElitistaConectivos;
import main.ag.strategy.AGElitistaFrequenciaAtomos;
import main.ag.strategy.AGElitistaMaiorComplexidade;
import main.ag.strategy.AGEstocasticoFrequenciaAtomos;
import main.ag.strategy.AGEstocasticoMaiorComplexidade;
import main.ag.strategy.IEstrategiaAG;
import main.ag.strategy.hibrido.AGHibFreqAtomosElitEsto;
import main.ag.strategy.hibrido.AGHibrMaiorComplexidadeElitEsto;

/**
 * <i>Factory</i> das estrat&eacute;gias de AG
 * @author Emerson Shigueo Sugimoto 10-12-2012
 * */
public class AGConfiguration {
	/**
	 * Representa as abordagens
	 * */
	public static enum Abordagens {
		EstocasticoMaiorComplexidade,
		EstocasticoFrequenciaAtomos,
		
		ElitistaMaiorComplexidade,
		ElitistaFrequenciaAtomos,
		//ElitistaConectivos,
		
		HibrMaiorComplexidadeElitEsto,
		HibFreqAtomosElitEsto,
		
		NotApplyAG
	}
	
	/**
	 * Recupera a abordagem a partir da String - entrada via linha de comando.
	 * @see Abordagens
	 * */
	public static Abordagens GetAbordagemFromString(String abordagem){
		if (abordagem.equalsIgnoreCase("EstocasticoMaiorComplexidade")) {
			return Abordagens.EstocasticoMaiorComplexidade;
		} else if (abordagem.equalsIgnoreCase("EstocasticoFrequenciaAtomos")) {
				return Abordagens.EstocasticoFrequenciaAtomos;
		} else if (abordagem.equalsIgnoreCase("ElitistaMaiorComplexidade")) {
			return Abordagens.ElitistaMaiorComplexidade;
		} else if (abordagem.equalsIgnoreCase("ElitistaFrequenciaAtomos")) {
			return Abordagens.ElitistaFrequenciaAtomos;
		//} else if (abordagem.equalsIgnoreCase("ElitistaConectivos")) {
		//	return Abordagens.ElitistaConectivos;
			
		} else if (abordagem.equalsIgnoreCase("HibrMaiorComplexidadeElitEsto")) {
			return Abordagens.HibrMaiorComplexidadeElitEsto;
		} else if (abordagem.equalsIgnoreCase("HibFreqAtomosElitEsto")) {
			return Abordagens.HibFreqAtomosElitEsto;
		}
		
		return Abordagens.NotApplyAG;
	}
	
	/**
	 * @param abordagem String Abordagem
	 * @return Estrat&eacute;gia de AG
	 * @see Abordagens
	 * @see main.ag.strategy.IEstrategiaAG
	 * @see main.ag.strategy.AGEstrategia
	 * */
	public static IEstrategiaAG GetEstrategiaAG(String abordagem){
		return GetEstrategiaAG(GetAbordagemFromString(abordagem));
	}
	
	/**
	 * @param abordagem enum Abordagens
	 * @return Retorna uma estrat&eacute;gia de AG baseada no enum {@link abordagem}
	 * @see Abordagens
	 * @see main.ag.strategy.IEstrategiaAG
	 * @see main.ag.strategy.AGEstrategia
	 * @author Emerson Shigueo Sugimoto 10-12-2012
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
		//case ElitistaConectivos:
		//	return new AGElitistaConectivos();

		case HibrMaiorComplexidadeElitEsto:
			return new AGHibrMaiorComplexidadeElitEsto();
		case HibFreqAtomosElitEsto:
			return new AGHibFreqAtomosElitEsto();
			
		case NotApplyAG:
		default:
			return null;
		}
	}
	
}