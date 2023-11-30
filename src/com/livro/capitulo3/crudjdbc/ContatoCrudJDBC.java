package com.livro.capitulo3.crudjdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ContatoCrudJDBC {
	public void salvar(Contato contato) {
		Connection conexao = this.geraconexao();
		PreparedStatement insereSt = null;

		String sql = "insert into contato(nome, telefone, email, dt_cad, obs) values (?, ?, ?, ?, ?)";
		try {
			insereSt = conexao.prepareStatement(sql);
			insereSt.setString(1, contato.getNome());
			insereSt.setString(2, contato.getTelefone());
			insereSt.setString(3, contato.getEmail());
			insereSt.setDate(4, contato.getDataCadastro());
			insereSt.setString(5, contato.getObservacao());
			insereSt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro ao incluir contato. Mensagem: " + e.getMessage());
		} finally {
			try {
				insereSt.close();
				conexao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar operações de inserção. Mensagem: " + e.getMessage());
			}
		}
	}

	public void atualizar(Contato contato) {
	}

	public void excluir(Contato contato) {
	}

	public List<Contato> listar() {
		Connection conexao = this.geraconexao();
		List<Contato> contatos = new ArrayList<Contato>();
		Statement consulta = null;
		ResultSet resultado = null;
		Contato contato = null;

		String sql = "select * from contato";

		try {
			consulta = conexao.createStatement();
			resultado = consulta.executeQuery(sql);

			while (resultado.next()) {
				contato = new Contato();
				contato.setCodigo(new Integer(resultado.getInt("codigo")));
				contato.setNome(resultado.getString("nome"));
				contato.setTelefone(resultado.getString("telefone"));
				contato.setEmail(resultado.getString("email"));
				contato.setDataCadastro(resultado.getDate("dt_cad"));
				contato.setObservacao(resultado.getString("obs"));
				contatos.add(contato);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao buscar código de contato. Mensagem: " + e.getMessage());
		} finally {
			try {
				consulta.close();
				resultado.close();
				conexao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar operações de consulta. Mensagem: " + e.getMessage());
			}
		}
		return contatos;
	}

	public Contato buscaContato(int valor) {
		return null;
	}

	public Connection geraconexao() {
		Connection conexao = null;
		try {
			// Registrando a classe JDBC no sistema em tempo de execução
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/agenda";
			String usuario = "root";
			String senha = "";

			conexao = DriverManager.getConnection(url, usuario, senha);
		} catch (ClassNotFoundException e) {
			System.out.println("Ocorreu um erro de SQL, Erro: " + e.getMessage());
		} catch(SQLException e) {
			System.out.println("Classe não encontrada. Erro + " + e.getMessage());
		}
		return conexao;
	}

	public static void main(String[] args) {
		ContatoCrudJDBC contatoCRUDJDBC = new ContatoCrudJDBC();

		// Criando o primeiro contato
		Contato beltrano = new Contato();
		beltrano.setNome("Beltrano Solar");
		beltrano.setTelefone("(47) 55555-3333");
		beltrano.setEmail("beltrano@teste.com.br");
		beltrano.setDataCadastro(new Date(System.currentTimeMillis()));
		beltrano.setObservacao("Novo cliente");
		contatoCRUDJDBC.salvar(beltrano);

		// Criando o segundo contato
		Contato ciclano = new Contato();
		ciclano.setNome("Ciclano Solar");
		ciclano.setTelefone("(47) 77777-2222");
		ciclano.setEmail("ciclano@teste.com.br");
		ciclano.setDataCadastro(new Date(System.currentTimeMillis()));
		ciclano.setObservacao("Novo cliente");
		contatoCRUDJDBC.salvar(ciclano);
		System.out.println("Contatos cadastrados: " + contatoCRUDJDBC.listar().size());

	}

}
