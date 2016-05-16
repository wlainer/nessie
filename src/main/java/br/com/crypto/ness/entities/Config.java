package br.com.crypto.ness.entities;

public class Config {

	private boolean salvarKey;
	private boolean salvarIV;
	private String key;
	private String IV;
	private String tempoApagarMensagem;

	public boolean isSalvarKey() {
		return salvarKey;
	}

	public void setSalvarKey(boolean salvarKey) {
		this.salvarKey = salvarKey;
	}

	public boolean isSalvarIV() {
		return salvarIV;
	}

	public void setSalvarIV(boolean salvarIV) {
		this.salvarIV = salvarIV;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIV() {
		return IV;
	}

	public void setIV(String iV) {
		IV = iV;
	}

	public String getTempoApagarMensagem() {
		if (tempoApagarMensagem == null || tempoApagarMensagem.isEmpty())
			return "10000";
		return tempoApagarMensagem;
	}

	public void setTempoApagarMensagem(String tempoApagarMensagem) {
		this.tempoApagarMensagem = tempoApagarMensagem;
	}
}
