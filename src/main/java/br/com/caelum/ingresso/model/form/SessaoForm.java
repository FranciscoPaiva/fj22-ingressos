package br.com.caelum.ingresso.model.form;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class SessaoForm {

	@NotNull
	private Integer salaId;

	@DateTimeFormat(pattern = "HH:mm")
	@NotNull
	private LocalTime horario;

	private BigDecimal preco;

	public Integer getSalaId() {
		return salaId;
	}

	public void setSalaId(Integer salaId) {
		this.salaId = salaId;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public BigDecimal getPreco() {
		return preco.setScale(2, RoundingMode.HALF_UP);
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	public Integer getFilmeId() {
		return filmeId;
	}

	public void setFilmeId(Integer filmeId) {
		this.filmeId = filmeId;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	@NotNull
	private Integer filmeId;

	public Sessao toSessao(SalaDao salaDao, FilmeDao filmeDao) {
		Filme filme = filmeDao.findOne(filmeId);
		Sala sala = salaDao.findOne(salaId);
		Sessao sessao = new Sessao(this.horario, filme, sala);
		return sessao;
	}
}
