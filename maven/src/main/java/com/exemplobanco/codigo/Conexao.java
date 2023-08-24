package com.exemplobanco.codigo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {
    public static void main(String[] args) throws SQLException, IOException {
        Connection conexao = null;
        try {
            // Carrega a classe do driver JDBC do PostgreSQL
            Class.forName("org.postgresql.Driver");
            
            // Estabelece a conexão com o banco de dados PostgreSQL
            conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "");
            
            // Declara variáveis para armazenar o login e a senha inseridos pelo usuário
            String login = "";
            String senha = "";
            
            // Define a consulta SQL para inserir dados na tabela "login"
            String query = "INSERT INTO login (login, senha) VALUES (?, ?)";
            
            // Prepara a leitura de entrada do usuário
            InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            
            // Executa uma consulta para obter todos os registros da tabela "login"
            ResultSet rsClient = conexao.createStatement().executeQuery("SELECT * FROM login");
            
            // Prepara uma instrução parametrizada para inserção na tabela "login"
            PreparedStatement preparedStatement = conexao.prepareStatement(query);
            
            // Itera pelos resultados da consulta e imprime os logins na saída
            while (rsClient.next()) {
                System.out.println("Nome: " + rsClient.getString("login"));
            }
            
            // Lê os valores de login e senha inseridos pelo usuário
            login = bufferedReader.readLine();
            senha = bufferedReader.readLine();
            
            // Define os parâmetros da instrução parametrizada
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, senha);
            
            // Executa a inserção na tabela "login"
            int linhasAfetadas = preparedStatement.executeUpdate();
            System.out.println("Novo registro adicionado. Linhas afetadas: " + linhasAfetadas);
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver do banco de dados nao localizado");
        } catch (SQLException ex) {
            System.out.println("Ocorreu erro ao acessar o banco" + ex.getMessage());
        } finally {
            if (conexao != null) {
                conexao.close();
            }
        }
    }
}
