package data.util;

public enum CompositionRule {
	Rule1{@Override
		public String toString() {
		return "PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS";
	}},
	Rule2{@Override
	public String toString() {
		return "PRIMEIRO OS SONS COM MAIS FAVORITOS";
	}},
	Rule3{@Override
		public String toString() {
			return "PRIMEIRO SONS DE FONTES DAS QUAIS FAVORITEI SONS NO PASSADO";
	}}
}
