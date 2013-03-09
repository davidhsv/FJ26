package br.com.caelum.notasfiscais.mb;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.notasfiscais.dao.DAO;
import br.com.caelum.notasfiscais.modelo.Produto;

@ManagedBean
@ViewScoped
public class ProdutoBean {
	private Produto produto = new Produto();
	private List<Produto> produtos;
	private double somatorio;

	public Produto getProduto() {
		return produto;
	}

	public void grava() {
		DAO<Produto> dao = new DAO<Produto>(Produto.class);
		dao.adiciona(produto);
		produto = new Produto();
		this.produtos = dao.listaTodos();
		this.calculaSomatorio();
	}

	public List<Produto> getProdutos() {
		if (produtos == null) {
			produtos = new DAO<Produto>(Produto.class).listaTodos();
		}
		this.calculaSomatorio();
		return produtos;
	}

	public void remove(Produto produto) {
		DAO<Produto> dao = new DAO<>(Produto.class);
		dao.remove(produto);
		this.produtos = dao.listaTodos();
		this.calculaSomatorio();
	}

	public void calculaSomatorio() {

		somatorio = 0;

		for (Produto produto : produtos) {
			this.somatorio += produto.getPreco();
		}
	}

	public double getSomatorio() {
		return somatorio;
	}

	public void comecaComMaiuscula(FacesContext fc, UIComponent component, Object object) {
		String valor = object.toString();
		if (!valor.matches("[A-Z].*")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com maiúscula"));
		}
	}

}
