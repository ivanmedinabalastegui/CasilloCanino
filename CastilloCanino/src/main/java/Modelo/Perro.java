package Modelo;

public class Perro {

    private String Nombre_Perro, Raza, Descripcion;
    private int IDPerro, Edad, Num_Cartilla;

    public Perro(int IDPerro, String Nombre_Perro, String Raza, int Edad, int Num_Cartilla, String Descripcion) {
        this.IDPerro = IDPerro;
        this.Nombre_Perro = Nombre_Perro;
        this.Raza = Raza;
        this.Edad = Edad;
        this.Num_Cartilla = Num_Cartilla;
        this.Descripcion = Descripcion;

    }

    public String getNombre_Perro() {return Nombre_Perro;}

    public void setNombre_Perro(String Nombre_Perro) {
        this.Nombre_Perro = Nombre_Perro;}

    public String getRaza() {return Raza;}

    public void setRaza(String Raza) {
        this.Raza = Raza;}

    public String getDescripcion() {return Descripcion;}

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;}

    public int getIDPerro() {return IDPerro;}

    public void setIDPerro(int IDPerro) {this.IDPerro = IDPerro;}

    public int getEdad() {return Edad;}

    public void setEdad(int Edad) {
        this.Edad = Edad;}

    public int getNum_Cartilla() {return Num_Cartilla;}

    public void setNum_Cartilla(int Num_Cartilla) {
        this.Num_Cartilla = Num_Cartilla;}
}
