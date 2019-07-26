package br.com.caelum.ingresso.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.caelum.ingresso.model.descontos.Desconto;
import br.com.caelum.ingresso.model.descontos.DescontoParaBancos;
import br.com.caelum.ingresso.model.descontos.DescontoParaEstudantes;
import br.com.caelum.ingresso.model.descontos.SemDesconto;

@Entity
public class Ingresso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Sessao sessao;

	@ManyToOne
	private Lugar lugar;

	private BigDecimal preco;

	@Enumerated(EnumType.STRING)
	private TipoDeIngresso tipoDeIngresso;

	public enum TipoDeIngresso {
		INTEIRO(new SemDesconto()), //
		ESTUDANTE(new DescontoParaEstudantes()), //
		BANCO(new DescontoParaBancos());

		private final Desconto desconto;

		TipoDeIngresso(Desconto desconto) {
			this.desconto = desconto;
		}

		public BigDecimal aplicaDesconto(BigDecimal valor) {
			return desconto.aplicarDescontoSobre(valor);
		}

		public String getDescricao() {
			return desconto.getDescricao();
		}
	}

	/**
	 * @deprecated hibernate only
	 */
	public Ingresso() {
	}

	public Ingresso(Sessao sessao, TipoDeIngresso tipoDeIngresso, Lugar lugar) {
		this.sessao = sessao;
		this.tipoDeIngresso = tipoDeIngresso;
		this.preco = this.tipoDeIngresso.aplicaDesconto(sessao.getPreco());
		this.lugar = lugar;
	}

	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public TipoDeIngresso getTipoDeIngresso() {
		return tipoDeIngresso;
	}

	public void setTipoDeIngresso(TipoDeIngresso tipoDeIngresso) {
		this.tipoDeIngresso = tipoDeIngresso;
	}

	public BigDecimal getPreco() {
		return preco.setScale(2, RoundingMode.HALF_UP);
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public Integer getId() {
		return id;
	}

}
