package Modelo;

import Controlador.IOBaseDatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Estancia {
    private int IDEstancia, IDCliente, IDPerro, IDCaseta;
    private String nombrePerro, nombreCliente, nombreCaseta;
    private Date Fecha_Ingreso, Fecha_Salida;

    private IOBaseDatos IO = new IOBaseDatos();

    public Estancia(int IDEstancia, Date Fecha_Ingreso, Date Fecha_Salida, int IDPerro, int IDCliente, int IDCaseta) {
        this.IDEstancia = IDEstancia;
        this.Fecha_Ingreso = Fecha_Ingreso;
        this.Fecha_Salida = Fecha_Salida;
        this.IDPerro = IDPerro;
        this.IDCliente = IDCliente;
        this.IDCaseta = IDCaseta;

        try {
            this.nombrePerro = buscarNombres("Perro", this.IDPerro);
            this.nombreCliente = buscarNombres("Cliente", this.IDCliente);
            this.nombreCaseta = buscarNombres("Caseta", this.IDCaseta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getIDEstancia() {
        return IDEstancia;
    }

    public void setIDEstancia(int IDEstancia) {
        this.IDEstancia = IDEstancia;
    }

    public int getIDCliente() {
        return IDCliente;
    }

    public void setIDCliente(int IDCliente) {
        this.IDCliente = IDCliente;
    }

    public int getIDPerro() {
        return IDPerro;
    }

    public void setIDPerro(int IDPerro) {
        this.IDPerro = IDPerro;
    }

    public int getIDCaseta() {
        return IDCaseta;
    }

    public void setIDCaseta(int IDCaseta) {
        this.IDCaseta = IDCaseta;
    }

    public Date getFecha_Ingreso() {
        return Fecha_Ingreso;
    }

    public void setFecha_Ingreso(Date Fecha_Ingreso) {
        Fecha_Ingreso = Fecha_Ingreso;
    }

    public Date getFecha_Salida() {
        return Fecha_Salida;
    }

    public void setFecha_Salida(Date Fecha_Salida) {
        Fecha_Salida = Fecha_Salida;
    }

    public String getNombrePerro() {
        return nombrePerro;
    }

    public void setNombrePerro(String nombrePerro) {
        this.nombrePerro = nombrePerro;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreCaseta() {
        return nombreCaseta;
    }

    public void setNombreCaseta(String nombreCaseta) {
        this.nombreCaseta = nombreCaseta;
    }

    public String getDNICliente() throws SQLException {
        ResultSet Consult = IO.introduceRegistros("SELECT DNI FROM CLIENTES WHERE IDCliente = " + this.IDCliente);
        Consult.next();

        return Consult.getString("DNI");
    }

    public String getNumCartilla() throws SQLException {
        ResultSet Consult = IO.introduceRegistros("SELECT Num_Cartilla FROM PERROS WHERE IDPerro = " + this.IDPerro);
        Consult.next();

        return Consult.getString("Num_Cartilla");
    }

    private String buscarNombres(String deQue, int ID) throws SQLException {
        String encontrado = "", columna = "", tabla = "", coincidir = "";
        switch (deQue) {
            case "Perro":
                columna = "Nombre_Perro";
                tabla = "PerroS";
                coincidir = "IDPerro";
                break;
            case "Cliente":
                columna = "CONCAT(Nombre_Cliente, ' ', Apellidos_Cliente) AS 'Nombre Completo'";
                tabla = "CLIENTES";
                coincidir = "IDCliente";
                break;
            case "Caseta":
                columna = "Nombre_Caseta";
                tabla = "CASETAS";
                coincidir = "IDCaseta";
                break;
        }
        ResultSet Consult = IO.introduceRegistros("SELECT " + columna + " FROM " + tabla + " WHERE " + coincidir + " = " + ID);
        Consult.next();

        switch (deQue) {
            case "Perro":
                return encontrado = Consult.getString("Nombre_Perro");
            case "Cliente":
                return encontrado = Consult.getString("Nombre Completo");
            case "Caseta":
                return encontrado = Consult.getString("Nombre_Caseta");
        }

        return encontrado = "Nada";
    }
}
