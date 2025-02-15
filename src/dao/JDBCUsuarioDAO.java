package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuario;
import util.Crypter;

public class JDBCUsuarioDAO extends JDBCDAO implements UsuarioDAO {

	protected JDBCUsuarioDAO() {

	}

	@Override
	public void cadastrar(Usuario usuario) {
		super.open();
		try {
			String SQL = "UPDATE pessoa_usuario SET login=?, senha=?, nivel=?, perfil=? WHERE id_pessoa_usuario=?";
			PreparedStatement ps = super.getConnection().prepareStatement(SQL);

			ps.setString(1, usuario.getLogin());
			ps.setString(2, usuario.getSenha());
			ps.setInt(3, usuario.getNivel() != null ? usuario.getNivel().getValorNivel() : 2);
			ps.setInt(4, usuario.getPerfil().getValorPerfil());
			ps.setInt(5, usuario.getPessoa().getId());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Falha ao cadastrar usu�rios em JDBCUsuaruioDAO.", e);
		} finally {
			super.close();
		}

	}

	@Override
	public void editar(Usuario usuario) {
		super.open();
		try {
			String SQL = "UPDATE pessoa_usuario SET login=?, nivel=?, perfil=? WHERE id_pessoa_usuario = ?";
			PreparedStatement ps = super.getConnection().prepareStatement(SQL);
			ps.setString(1, usuario.getLogin());
			ps.setInt(2, usuario.getNivel().getValorNivel());
			ps.setInt(3, usuario.getPerfil().getValorPerfil());
			ps.setInt(4, usuario.getPessoa().getId());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao editar registro de usuario.",e);
		} finally {
			super.close();
		}

	}

	@Override
	public void editarUsuarioESenha(Usuario usuario) {
		super.open();
		try {
			String SQL = "UPDATE pessoa_usuario SET login=?, senha=? WHERE id_pessoa_usuario = ?";
			PreparedStatement ps = super.getConnection().prepareStatement(SQL);
			ps.setString(1, usuario.getLogin());
			ps.setString(2, usuario.getSenha());
			ps.setInt(3, usuario.getPessoa().getId());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao editar registro de usuario. ", e);
		} finally {
			super.close();
		}

	}
	
	@Override
	public boolean autenticar(String login, String senha) {
		super.open();
		try {
			String SQL = "SELECT * FROM pessoa_usuario as u WHERE u.login = ? OR u.email = ?";
			PreparedStatement ps = super.getConnection().prepareStatement(SQL);

			ps.setString(1, login);
			ps.setString(2, login);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getString("senha").equals(Crypter.crypt(senha))) {
					return true;
				}
			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			throw new RuntimeException("Erro: login e senha inv�lidos.");
		} finally {
			super.close();
		}
		return false;
	}

	@Override
	public void editarNivel(Usuario usuario) {
		super.open();
		try {
			String SQL = "UPDATE pessoa_usuario SET nivel =? WHERE id_pessoa_usuario =?";

			PreparedStatement ps = super.getConnection().prepareStatement(SQL);
			ps.setInt(1, usuario.getNivel().getValorNivel());
			ps.setInt(2, usuario.getPessoa().getId());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao editar n�vel de usuario.");
		} finally {
			super.close();
		}

	}

	@Override
	public void salvarToken(String token, int id_usuario) {
		super.open();

		try {
			String SQL = "UPDATE pessoa_usuario SET token_sessao =? WHERE id_pessoa_usuario = ?";
			PreparedStatement ps = super.getConnection().prepareStatement(SQL);
			ps.setString(1, token);
			ps.setInt(2, id_usuario);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao gravar token do usu�rio.",e);
		} finally {
			super.close();
		}
	}

	@Override
	public void salvarTokenUsuario(String token, int id_usuario) {
		super.open();
		try {
			String SQL = "UPDATE pessoa_usuario SET token_usuario =? WHERE id_pessoa_usuario = ?";
			PreparedStatement ps = super.getConnection().prepareStatement(SQL);
			ps.setString(1, token);
			ps.setInt(2, id_usuario);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao gravar token do usu�rio.",e);
		} finally {
			super.close();
		}
	}
	
	@Override
	public String buscarToken(int id_pessoa) {
		super.open();
		String token = "";
		String SQL = "SELECT token_sessao FROM public.pessoa_usuario WHERE id_pessoa_usuario = ?";
		try {
			PreparedStatement ps = super.getConnection().prepareStatement(SQL);
			ps.setInt(1, id_pessoa);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				token = rs.getString("token_sessao");
				ps.close();
				rs.close();
				return token;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Falha ao buscar token de sess�o. Erro: " + e.getMessage());
		} finally {
			super.close();
		}
	}

	@Override
	public String buscarTokenTemp(int id_pessoa) {
		super.open();
		String token = "";
		String SQL = "SELECT token_usuario FROM public.pessoa_usuario WHERE id_pessoa_usuario = ?";
		try {
			PreparedStatement ps = super.getConnection().prepareStatement(SQL);
			ps.setInt(1, id_pessoa);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				token = rs.getString("token_usuario");
				ps.close();
				rs.close();
				return token;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Falha ao buscar token de sess�o. Erro: " + e.getMessage());
		} finally {
			super.close();
		}
	}

}
