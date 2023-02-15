package Modelo;

public class Caseta {

    private int IDCaseta, Tarifa, Cap_Perros, Perros_Dentro;
    private String Tamanho, Nombre_Caseta;
    private CustomProgressBar barra;

    public Caseta(int IDCaseta, String Nombre_Caseta, String Tamanho, int Cap_Perros, int Perros_Dentro, int Tarifa  ) {
        this.IDCaseta = IDCaseta;
        this.Nombre_Caseta = Nombre_Caseta;
        this.Tamanho = Tamanho;
        this.Cap_Perros = Cap_Perros;
        this.Perros_Dentro = Perros_Dentro;
        this.Tarifa = Tarifa;

        this.barra = new CustomProgressBar(Perros_Dentro, Cap_Perros);
    }

    public int getIDCaseta() {return IDCaseta;}

    public void setIDCaseta(int IDCaseta) {this.IDCaseta = IDCaseta;}

    public int getTarifa() {return Tarifa;}

    public void setTarifa(int Tarifa) {
        this.Tarifa = Tarifa;}

    public int getCap_Perros() {return Cap_Perros;}

    public void setCap_Perros(int Cap_Perros) {
        this.Cap_Perros = Cap_Perros;}

    public int getPerros_dentro() {return Perros_Dentro;}

    public void setPerros_dentro(int Perros_Dentro) {
        this.Perros_Dentro = Perros_Dentro;}

    public String getTamanho() {return Tamanho;}

    public void setTamanho(String Tamanho) {
        this.Tamanho = Tamanho;}

    public String getNombre_Caseta() {return Nombre_Caseta;}

    public void setNombre_Caseta(String Nombre_Caseta) {
        this.Nombre_Caseta = Nombre_Caseta;}

    public CustomProgressBar getBarra() {
        return barra;
    }

    public void setBarra(CustomProgressBar barra) {
        this.barra = barra;
    }
}
