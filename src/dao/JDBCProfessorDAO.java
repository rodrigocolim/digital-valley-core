package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Professor;

public class JDBCProfessorDAO extends JDBCDAO implements ProfessorDAO{

	

	protected JDBCProfessorDAO() {
	
	}
		
	@Override
	public void cadastrar(Professor professor) {
		super.open();
		try {
			String SQL = "INSERT INTO professor (id_pessoa_prof) VALUES " + " (?)";
			PreparedStatement ps = super.getConnection().prepareStatement(SQL);
			
			ps.setInt(1, professor.getId());
			ps.executeUpdate();
			ps.close();			

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Falha ao cadastrar professor em JDBCProfessorDAO.", e);
		}finally {
			super.close();
		}
		
	}

	@Override
	public Professor buscar(int id) {
		super.open();
		String SQL = "SELECT * FROM professor AS p, pessoa_usuario AS u, servidor AS s WHERE p.id_pessoa_prof=? AND u.id_pessoa_usuario = p.id_pessoa_prof AND p.id_pessoa_prof = s.id_pessoa_usuario";
		try {

			PreparedStatement ps = super.getConnection().prepareStatement(SQL);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				Professor professor = new Professor();				
				professor.setId(rs.getInt("id_pessoa_prof"));
				professor.setNome(rs.getString("nome"));
				professor.setCpf(rs.getString("cpf"));
				professor.setDataNascimento(LocalDate.parse(rs.getString("data_nascimento")));
				professor.setEmail(rs.getString("email"));
				professor.setSiape(rs.getString("siape"));
				rs.close();
				ps.close();
				
				return professor;
			}else{
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar registro de professor.", e);
		}finally {
			super.close();
		}
	}
	
	@Override
	public Professor buscarPorSiape(String siape) {
		super.open();
		String SQL = "SELECT * FROM servidor AS s, professor AS prof, pessoa_usuario AS u WHERE s.siape = ? AND s.id_pessoa_usuario = u.id_pessoa_usuario AND u.id_pessoa_usuario =  prof.id_pessoa_prof";
		try {

			PreparedStatement ps = super.getConnection().prepareStatement(SQL);
			ps.setString(1, siape);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				Professor professor = new Professor();				
				professor.setId(rs.getInt("id_pessoa_prof"));
				professor.setNome(rs.getString("nome"));
				professor.setCpf(rs.getString("cpf"));
				professor.setDataNascimento(LocalDate.parse(rs.getString("data_nascimento")));
				professor.setEmail(rs.getString("email"));
				professor.setSiape(rs.getString("siape"));
				rs.close();
				ps.close();
				
				return professor;
			}else{
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar registro de professor.", e);
		}finally {
			super.close();
		}
	}

	
	@Override
	public List<Professor> listar() {
		super.open();
		List<Professor> professores = new ArrayList<Professor>();
		try {
			String SQL = "SELECT * FROM professor AS p, pessoa_usuario AS u, servidor AS s WHERE u.id_pessoa_usuario = p.id_pessoa_prof AND u.id_pessoa_usuario = s.id_pessoa_usuario";
			PreparedStatement ps = super.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Professor professor = new Professor();				
				professor.setId(rs.getInt("id_pessoa_prof"));
				professor.setNome(rs.getString("nome"));
				professor.setCpf(rs.getString("cpf"));
				professor.setDataNascimento(LocalDate.parse(rs.getString("data_nascimento")));
				professor.setEmail(rs.getString("email"));
				professor.setSiape(rs.getString("siape"));
				
				professores.add(professor);
				
			}

			ps.close();
			rs.close();
			
			return professores;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Falha ao listar professor em JDBCProfessorDAO.", e);

		}finally {
			super.close();
		}

	}
	
	@Override
	public List<Professor> buscarContains(String nome) {
		super.open();
		List<Professor> professores = new ArrayList<Professor>();
		try {
			String SQL = "SELECT id_pessoa_prof, nome FROM professor AS p, pessoa_usuario AS u WHERE u.id_pessoa_usuario = p.id_pessoa_prof AND UPPER(u.nome) like UPPER(?)";
			PreparedStatement ps = super.getConnection().prepareStatement(SQL);
			ps.setString(1, '%'+nome+'%');
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Professor professor = new Professor();				
				professor.setId(rs.getInt("id_pessoa_prof"));
				professor.setNome(rs.getString("nome"));
				professores.add(professor);
				
			}

			ps.close();
			rs.close();
			
			return professores;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Falha ao listar professor em JDBCProfessorDAO.", e);

		}finally {
			super.close();
		}

	}


}
