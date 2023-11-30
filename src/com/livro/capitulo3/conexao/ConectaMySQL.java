package com.livro.capitulo3.conexao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConectaMySQL {
	public static void main(String[] args) {
		Connection conexao = null;
		try {
			// Registrando a classe JDBC no sistema em tempo de execução
			String url = "jdbc:mysql://localhost/agenda";
			String usuario = "root";
			String senha = "";

			conexao = DriverManager.getConnection(url, usuario, senha);
			System.out.println("Conectado!");
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro de SQL, Erro: " + e.getMessage());
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar a conexão. Erro: " + e.getMessage());
			}
		}

	}
}
