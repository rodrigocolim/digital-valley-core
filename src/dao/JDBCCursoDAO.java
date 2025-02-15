package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Curso;

public class JDBCCursoDAO extends JDBCDAO implements CursoDAO{
	

	
	protected JDBCCursoDAO() {
		
	}
	

	@Override
	public void cadastrar(Curso curso) {
		super.open();
		try {
			String SQL = "INSERT INTO curso(nome_curso, id_curso, professor_coordenador_id) VALUES (?, ?, ?)";
			PreparedStatement ps = super.getConnection().prepareStatement(SQL);

			ps.setString(1, curso.getNome());
			ps.setInt(2, curso.getId());

			ps.executeUpdate();
			ps.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Falha ao cadastrar curso. Erro: "+ e.getMessage());
		}finally {
			super.close();
		}
		
	}

	@Override
	public void editar(Curso curso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remover(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Curso buscar(int id) {		
		try {
			super.open();		
			Curso curso = null;
			PreparedStatement ps = super.getConnection().prepareStatement("SELECT * FROM curso WHERE id_curso = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				curso = new Curso();
				curso.setNome(rs.getString("nome_curso"));
				curso.setId(rs.getInt("id_curso"));
				curso.setCoordenador(DAOFactory.criarProfessorDAO().buscar(rs.getInt("professor_coordenador_id")));
						
				ps.close();
				rs.close();
			}
			return curso;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar registro do curso. Erro: "+e.getMessage());
		}finally {
			super.close();
		}
	}
	
	@Override
	public Curso buscarPorNome(String nome){
		super.open();
		Curso curso = new Curso();
		String SQL = "SELECT nome_curso, id_curso FROM curso WHERE UPPER(nome_curso) like UPPER( ? )";
		try {

			PreparedStatement ps = super.getConnection().prepareStatement(SQL);

			ps.setString(1, "%"+nome+"%");

			ResultSet rs = ps.executeQuery();
			rs.next();
				
			curso.setNome(rs.getString("nome_curso"));
			curso.setId(rs.getInt("id_curso"));
			
			ps.close();
			rs.close();

			return curso;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar registro do curso. Erro: "+e.getMessage());
		}finally {
			super.close();
		}

	}

}
