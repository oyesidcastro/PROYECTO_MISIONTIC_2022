package restful.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import restful.Model.ClienteModel;
import restful.Model.Conexion;

public class ClienteService {

    public ArrayList<ClienteModel> getClientes() {
        ArrayList<ClienteModel> lista = new ArrayList<>();
        Conexion conn = new Conexion();
        String sql = "SELECT * FROM proveedores";

        try {
            Statement stm = conn.getCon().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                ClienteModel cliente = new ClienteModel();
                cliente.setCodigo(rs.getInt("codigo"));
                cliente.setNombres(rs.getString("nombres"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                lista.add(cliente);
            }
        } catch (SQLException e) {
        }

        return lista;
    }

    public ClienteModel getCliente(int id) {
        ClienteModel cliente = new ClienteModel();
        Conexion conex = new Conexion();
        String Sql = "SELECT * FROM proveedores WHERE codigo = ?";

        try {

            PreparedStatement pstm = conex.getCon().prepareStatement(Sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {

                cliente.setCodigo(rs.getInt("codigo"));
                cliente.setNombres(rs.getString("nombres"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return cliente;
    }

    public ClienteModel addCliente(ClienteModel cliente) {
        Conexion conex = new Conexion();
        String Sql = "INSERT INTO proveedores(codigo,nombres,direccion,telefono,email)";
        Sql = Sql + "values (?,?,?,?,?)";

        try {
            PreparedStatement pstm = conex.getCon().prepareStatement(Sql);
            pstm.setInt(1, cliente.getCodigo());
            pstm.setString(2, cliente.getNombres());
            pstm.setString(3, cliente.getDireccion());
            pstm.setString(4, cliente.getTelefono());
            pstm.setString(5, cliente.getEmail());
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return cliente;
    }

    public ClienteModel updateCliente(ClienteModel cliente) {
        Conexion conn = new Conexion();
        String sql = "UPDATE proveedores SET nombres=?,telefono=?,direccion=?,email=? WHERE codigo= ?";
        try {
            PreparedStatement pstm = conn.getCon().prepareStatement(sql);
            pstm.setString(1, cliente.getNombres());
            pstm.setString(2, cliente.getTelefono());
            pstm.setString(3, cliente.getDireccion());
            pstm.setString(4, cliente.getEmail());
            pstm.setInt(5, cliente.getCodigo());
            pstm.executeUpdate();
        } catch (SQLException excepcion) {
            System.out.println("Ha ocurrido un error al eliminar  " + excepcion.getMessage());
            return null;
        }
        return cliente;
    }

    public String delCliente(int codigo) {
        Conexion conn = new Conexion();

        String sql = "DELETE FROM proveedores WHERE codigo= ?";
        try {
            PreparedStatement pstm = conn.getCon().prepareStatement(sql);
            pstm.setInt(1, codigo);
            pstm.executeUpdate();
        } catch (SQLException excepcion) {
            System.out.println("Ha ocurrido un error al eliminar  " + excepcion.getMessage());
            return "{\"Accion\":\"Error\"}";
        }
        return "{\"Accion\":\"Registro Borrado\"}";
    }
}
