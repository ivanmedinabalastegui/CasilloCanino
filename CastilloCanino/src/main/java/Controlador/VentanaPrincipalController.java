package Controlador;

import Modelo.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VentanaPrincipalController implements Initializable {

    @FXML
    private ComboBox<String> menuModulos;
    @FXML
    private Accordion panelFiltros;
    @FXML
    private JFXButton botonLimpiarFiltros, reporteEstancia;

    // Módulo Almacén.
    @FXML
    private JFXTabPane tabsAlmacen;
    @FXML
    private Tab tabProductos, tabProveedores;
    @FXML
    public TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, String> colNomProduc, colTipoProduc, colPorvProduc, colObserProduc;
    @FXML
    private TableColumn<Producto, Integer> colIDProduc, colCantProduc;
    @FXML
    private JFXTextField textFiltroIDProd, textFiltroNombreProd, textFiltroProveedor, textFiltroTipo, textFiltroCantidad;
    @FXML
    public TableView<Proveedor> tablaProveedor;
    @FXML
    private TableColumn<Producto, String> colProvNom, colProvDirec, colProvTel, colProvPais;
    @FXML
    private TableColumn<Producto, Integer> colProvID;
    @FXML
    private JFXTextField textFiltroIDProv, textFiltroNombreProv, textFiltroTelefono, textFiltroPais;
    @FXML
    private Label labelNomProdProv;
    @FXML
    public Text labelAlmacen, miniLabelAlmacen;
    @FXML
    public Line lineaAlamacen;
    @FXML
    private JFXButton botonAnadir, botonModificar, botonEliminar;
    @FXML
    private ImageView imgAlmacen;

    // Módulo Facturación.
    @FXML
    public JFXTabPane tabsFacturacion;
    @FXML
    public Tab tabPerros, tabCliente, tabCaseta, tabEstancia;
    @FXML
    public TableView<Perro> tablaPerros;
    @FXML
    public TableColumn<Perro, Integer> colIDPerro, colEdadPerro;
    @FXML
    public TableColumn<Perro, String> colNombrePerro, colRazaPerro, colNum_cartillaPerro, colDescPerro;
    @FXML
    private JFXTextField textFiltroIDPerro, textFiltroNombrePerro, textFiltroRazaPerro, textFiltroEdadPerro, textFiltroCartillaPerro;
    @FXML
    public TableView<Cliente> tablaClientes;
    @FXML
    public TableColumn<Cliente, Integer> colIDCliente;
    @FXML
    public TableColumn<Cliente, String> colDNICliente, colNombreCliente, colApellidosCliente, colTelf1Cliente, colTelef2Cliente, colEmailCliente, colTipoPagoCliente;
    @FXML
    private JFXTextField textFiltroIDCliente, textFiltroNombreCliente, textFiltroApellidosCliente, textFiltroTel1Cliente, textFiltroTel2Cliente, textFiltroEmailCliente, textFiltroTipoPagoCliente;
    @FXML
    public TableView<Caseta> tablaCasetas;
    @FXML
    public TableColumn<Caseta, Integer> colIDCaseta, colCapCaseta, colCasetasAC, colTarifaCasetas;
    @FXML
    public TableColumn<Caseta, String> colNombreCaseta, colTamanoCaseta;
    @FXML
    public TableView<Estancia> tablaEstancia;
    @FXML
    public TableColumn<Estancia, Integer> colIDEstancia;
    @FXML
    public TableColumn<Estancia, Date> colFechaIngresoEstancia, colFechaSalidaEstancia;
    @FXML
    public TableColumn<Estancia, String> colNombrePerroEstancia, colNombreClienteEstancia, colNombreCasetaEstancia;
    @FXML
    private JFXTextField textFiltroIDEstancia, textFiltroIngresoEstancia, textFiltroSalidaEstancia, textFiltroNomPerroEstancia, textFiltroNomClienteEstancia, textFiltroNomCasetaEstancia;
    @FXML
    private Label labelNomPerro, labelNomCliente, labelNomCaseta;
    @FXML
    private ImageView imagePerro, imageCaseta, imageCliente;

    // Módulo Empleados.
    @FXML
    public Label labelNoModifiEmpleados, labelNombreEmpleado;
    @FXML
    public JFXListView<CustomListView> listViewEmpleados = new JFXListView<CustomListView>();
    @FXML
    public JFXListView<CustomListView> listViewEmpleadosActivos = new JFXListView<CustomListView>();
    @FXML
    public Rectangle rectanguloEmpleados;
    @FXML
    public Pane tarjetaEmplePanel;
    @FXML
    public ImageView tarjetaEmpleImg, tarjetaEmpleImgCaseta;
    @FXML
    public Label tarjetaTituloEmple, tarjetaEmpleNom, tarjetaEmpleApe, tarjetaEmpleDNI, tarjetaEmpleTel, tarjetaEmpleEmail, tarjetaEmpleCargo, tarjetaEmpleHorario, tarjetaEmpleTurno, tarjetaEmpleCaseta;
    @FXML
    public JFXButton botonAnadirEmple, botonModificarEmple, botonEliminarEmple;

    //Instancia de la clase IOBaseDatos.
    private final IOBaseDatos IO = new IOBaseDatos();

    //Listas para contener los elementos de los Modelos.
    public ObservableList<Producto> ListaProductos = FXCollections.observableArrayList();
    private final ObservableList<Proveedor> ListaProveedor = FXCollections.observableArrayList();
    private final ObservableList<Perro> ListaPerros = FXCollections.observableArrayList();
    private final ObservableList<Cliente> ListaClientes = FXCollections.observableArrayList();
    private final ObservableList<Caseta> ListaCasetas = FXCollections.observableArrayList();
    private final ObservableList<Estancia> ListaEstancias = FXCollections.observableArrayList();
    private final ObservableList<Empleado> ListaEmpleados = FXCollections.observableArrayList();

    //Listas para contener los elementos filtrados de las tablas.
    private final ObservableList<Producto> listaFiltrosProd = FXCollections.observableArrayList();
    private final ObservableList<Proveedor> listaFiltrosProv = FXCollections.observableArrayList();
    private final ObservableList<Perro> listaFiltrosPerros = FXCollections.observableArrayList();
    private final ObservableList<Cliente> listaFiltrosClientes = FXCollections.observableArrayList();
    private final ObservableList<Estancia> listaFiltrosEstancia = FXCollections.observableArrayList();

    //Listas para contener información sobre elementos y usarlos en los filtros.
    private final ArrayList<Integer> IDProdFiltros = new ArrayList<Integer>();
    private final ArrayList<String> nombresProdFiltros = new ArrayList<String>();
    private final ArrayList<String> proveedorProdFiltros = new ArrayList<String>();
    private final ArrayList<String> tipoProdFiltros = new ArrayList<String>();
    private final ArrayList<Integer> cantidadProdFiltros = new ArrayList<Integer>();

    private final ArrayList<Integer> IDProvFiltros = new ArrayList<Integer>();
    private final ArrayList<String> nombresProvFiltros = new ArrayList<String>();
    private final ArrayList<String> telefonoProvFiltros = new ArrayList<String>();
    private final ArrayList<String> paisProvFiltros = new ArrayList<String>();

    private final ArrayList<Integer> IDPerroFiltros = new ArrayList<Integer>();
    private final ArrayList<String> nombresPerroFiltros = new ArrayList<String>();
    private final ArrayList<String> razasPerroFiltros = new ArrayList<String>();
    private final ArrayList<Integer> edadPerroFiltros = new ArrayList<Integer>();
    private final ArrayList<Integer> cartillasPerroFiltros = new ArrayList<Integer>();

    private final ArrayList<Integer> IDClienteFiltros = new ArrayList<Integer>();
    private final ArrayList<String> DNIClienteFiltros = new ArrayList<String>();
    private final ArrayList<String> nombresClienteFiltros = new ArrayList<String>();
    private final ArrayList<String> apellidosClienteFiltros = new ArrayList<String>();
    private final ArrayList<String> telefono1ClienteFiltros = new ArrayList<String>();
    private final ArrayList<String> telefono2ClienteFiltros = new ArrayList<String>();
    private final ArrayList<String> emailClienteFiltros = new ArrayList<String>();
    private final ArrayList<String> tipoPagoClienteFiltros = new ArrayList<String>();

    private final ArrayList<Integer> IDEstanciaFiltros = new ArrayList<Integer>();
    private final ArrayList<Date> fechaIngresoEstanciaFiltros = new ArrayList<Date>();
    private final ArrayList<Date> fechaSalidaEstanciaFiltros = new ArrayList<Date>();
    private final ArrayList<String> nomPerroEstanciaFiltros = new ArrayList<String>();
    private final ArrayList<String> nomClienteEstanciaFiltros = new ArrayList<String>();
    private final ArrayList<String> nomCasetaEstanciaFiltros = new ArrayList<String>();

    private final ObservableList<String> nombresCasetasFiltros = FXCollections.observableArrayList();

    private final ArrayList<String> cargosExistentes = new ArrayList<String>();

    /*___________________________________________________________________________________________________________________________________________________________________________*/
    //Lo primero que se ejecutará al iniciar el programa
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciaTablas();
        iniciaTodo();
    }

    public void iniciaTodo() {

        try {
            iniciaRegistros();
            iniciaFiltros();
            iniciaListView();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*___________________________________________________________________________________________________________________________________________________________________________*/
    //Rellena las listas con los datos de la BD.
    public void iniciaRegistros() throws SQLException {
        ListaProductos.clear();
        ListaProveedor.clear();
        ListaPerros.clear();
        ListaClientes.clear();
        ListaCasetas.clear();
        ListaEstancias.clear();
        ListaEmpleados.clear();

        /******Para Productos******/
        //Consulta Productos.
        ResultSet r1 = IO.introduceRegistros("SELECT * FROM PRODUCTOS");

        while (r1.next()) {

            //Crea el nuevo producto.
            Producto prod = new Producto(r1.getInt("IDProducto"),
                    r1.getString("Nombre_Producto"),
                    r1.getString("Tipo_Producto"),
                    r1.getInt("Cantidad"),
                    r1.getInt("Minimo"),
                    r1.getDouble("precio"),
                    r1.getString("Observaciones"),
                    r1.getInt("IDProveedor"));

            //Los guarda en la lista y los muestra en la tabla.
            if (r1 != null) {

                this.ListaProductos.add(prod);
            }

            this.tablaProductos.setItems(ListaProductos);
            this.tablaProductos.refresh();
        }
        /**************************/

        /******Para Proveedores******/
        //Consulta Proveedores.
        ResultSet r2 = IO.introduceRegistros("SELECT * FROM PROVEEDOR");

        while (r2.next()) {

            //Crea el nuevo proveedor.
            Proveedor prov = new Proveedor(r2.getInt("IDProveedor"),
                    r2.getString("Nombre_Proveedor"),
                    r2.getString("Direccion"),
                    r2.getString("Telefono_Proveedor"),
                    r2.getString("Pais"));

            //Los guarda en la lista y los muestra en la tabla.
            if (r2 != null) {

                this.ListaProveedor.add(prov);
            }
            this.tablaProveedor.setItems(ListaProveedor);
            this.tablaProveedor.refresh();
        }
        /****************************/

        /******Para Perros******/
        //Consulta Perros.
        ResultSet r3 = IO.introduceRegistros("SELECT * FROM PerroS");

        while (r3.next()) {

            //Crea el nuevo Perro.
            Perro perr = new Perro(r3.getInt("IDPerro"),
                    r3.getString("Nombre_Perro"),
                    r3.getString("Raza"),
                    r3.getInt("Edad"),
                    r3.getInt("Num_Cartilla"),
                    r3.getString("Descripcion"));

            //Los guarda en la lista y los muestra en la tabla.
            if (r3 != null) {

                this.ListaPerros.add(perr);
            }

            this.tablaPerros.setItems(ListaPerros);
            this.tablaPerros.refresh();
        }
        /**********************/

        /******Para Clientes******/
        //Consulta Clientes.
        ResultSet r4 = IO.introduceRegistros("SELECT * FROM CLIENTES");

        while (r4.next()) {

            //Crea el nuevo Cliente.
            Cliente clie = new Cliente(r4.getInt("IDCliente"),
                    r4.getString("DNI"),
                    r4.getString("Nombre_Cliente"),
                    r4.getString("Apellidos_Cliente"),
                    r4.getString("Telefono_Cliente1"),
                    r4.getString("Telefono_Cliente2"),
                    r4.getString("Email_Cliente"),
                    r4.getString("TipoPago"));

            //Los guarda en la lista y los muestra en la tabla.
            if (r4 != null) {

                this.ListaClientes.add(clie);
            }

            this.tablaClientes.setItems(ListaClientes);
            this.tablaClientes.refresh();
        }
        /*************************/

        /******Para Casetas******/
        //Consulta Casetas.
        ResultSet r5 = IO.introduceRegistros("SELECT * FROM CasetaS");

        while (r5.next()) {

            //Crea el nuevo Caseta.
            Caseta caset = new Caseta(r5.getInt("IDCaseta"),
                    r5.getString("Nombre_Caseta"),
                    r5.getString("Tamanho"),
                    r5.getInt("Cap_Perros"),
                    r5.getInt("Perros_Dentro"),
                    r5.getInt("Tarifa"));

            //Los guarda en la lista y los muestra en la tabla.
            if (r5 != null) {

                this.ListaCasetas.add(caset);
            }

            this.tablaCasetas.setItems(ListaCasetas);
            this.tablaCasetas.refresh();
        }
        /**********************/

        /******Para Estancias******/
        //Consulta Estancia.
        ResultSet r6 = IO.introduceRegistros("SELECT * FROM ESTANCIA");

        while (r6.next()) {

            //Crea la nueva estancia.
            Estancia est = new Estancia(r6.getInt("IDEstancia"),
                    r6.getDate("Fecha_Ingreso"),
                    r6.getDate("Fecha_Salida"),
                    r6.getInt("IDPerro"),
                    r6.getInt("IDCliente"),
                    r6.getInt("IDCaseta"));

            //Los guarda en la lista y los muestra en la tabla.
            if (r6 != null) {

                this.ListaEstancias.add(est);
            }

            this.tablaEstancia.setItems(ListaEstancias);
            this.tablaEstancia.refresh();
        }
        /**************************/

        /******Para Empleados******/
        //Consulta Empleados.
        ResultSet r7 = IO.introduceRegistros("SELECT * FROM EMPLEADOS");

        while (r7.next()) {

            //Crea el nuevo empleado.
            Empleado emple = new Empleado(r7.getInt("IDEmpleado"),
                    r7.getString("DNI_Empleado"),
                    r7.getString("Nombre_Empleado"),
                    r7.getString("Apellidos_Empleado"),
                    r7.getString("Telefono_Empleado"),
                    r7.getString("Email_Empleado"),
                    r7.getString("Cargo"),
                    r7.getString("Horario_Trabajo"),
                    r7.getString("Turno"),
                    r7.getInt("IDCaseta"));

            //Los guarda en la lista.
            if (r7 != null) {

                this.ListaEmpleados.add(emple);
            }
        }
    }
    /**************************/

    //Indica a cada celda de las tablas que tipo de valor almacenarán.
    public void iniciaTablas() {

        //Columnas Productos.
        this.colIDProduc.setCellValueFactory(new PropertyValueFactory<>("IDProducto"));
        this.colNomProduc.setCellValueFactory(new PropertyValueFactory<>("Nombre_Producto"));
        this.colTipoProduc.setCellValueFactory(new PropertyValueFactory<>("Tipo_Producto"));
        this.colCantProduc.setCellValueFactory(new PropertyValueFactory<>("Cantidad"));
        this.colObserProduc.setCellValueFactory(new PropertyValueFactory<>("Observaciones"));
        this.colPorvProduc.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));

        //Columnas Proveedor.Perro
        this.colProvID.setCellValueFactory(new PropertyValueFactory<>("IDProveedor"));
        this.colProvNom.setCellValueFactory(new PropertyValueFactory<>("Nombre_Proveedor"));
        this.colProvDirec.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        this.colProvTel.setCellValueFactory(new PropertyValueFactory<>("Telefono_Proveedor"));
        this.colProvPais.setCellValueFactory(new PropertyValueFactory<>("Pais"));

        //Columnas Perros.
        this.colIDPerro.setCellValueFactory(new PropertyValueFactory<>("IDPerro"));
        this.colNombrePerro.setCellValueFactory(new PropertyValueFactory<>("Nombre_Perro"));
        this.colRazaPerro.setCellValueFactory(new PropertyValueFactory<>("Raza"));
        this.colEdadPerro.setCellValueFactory(new PropertyValueFactory<>("Edad"));
        this.colNum_cartillaPerro.setCellValueFactory(new PropertyValueFactory<>("Num_Cartilla"));
        this.colDescPerro.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));

        //Columnas Clientes.
        this.colIDCliente.setCellValueFactory(new PropertyValueFactory<>("IDCliente"));
        this.colDNICliente.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        this.colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("Nombre_Cliente"));
        this.colApellidosCliente.setCellValueFactory(new PropertyValueFactory<>("Apellidos_Cliente"));
        this.colTelf1Cliente.setCellValueFactory(new PropertyValueFactory<>("Telefono_Cliente1"));
        this.colTelef2Cliente.setCellValueFactory(new PropertyValueFactory<>("Telefono_Cliente2"));
        this.colEmailCliente.setCellValueFactory(new PropertyValueFactory<>("Email_Cliente"));
        this.colTipoPagoCliente.setCellValueFactory(new PropertyValueFactory<>("TipoPago"));

        //Columnas Casetas.
        this.colIDCaseta.setCellValueFactory(new PropertyValueFactory<>("IDCaseta"));
        this.colNombreCaseta.setCellValueFactory(new PropertyValueFactory<>("Nombre_Caseta"));
        this.colTamanoCaseta.setCellValueFactory(new PropertyValueFactory<>("Tamanho"));
        this.colCapCaseta.setCellValueFactory(new PropertyValueFactory<>("Cap_Perros"));
        this.colCasetasAC.setCellValueFactory(new PropertyValueFactory<>("barra"));
        this.colTarifaCasetas.setCellValueFactory(new PropertyValueFactory<>("Tarifa"));

        //Columnas Estancia.
        this.colIDEstancia.setCellValueFactory(new PropertyValueFactory<>("IDEstancia"));
        this.colFechaIngresoEstancia.setCellValueFactory(new PropertyValueFactory<>("Fecha_Ingreso"));
        this.colFechaSalidaEstancia.setCellValueFactory(new PropertyValueFactory<>("Fecha_Salida"));
        this.colNombrePerroEstancia.setCellValueFactory(new PropertyValueFactory<>("nombrePerro"));
        this.colNombreClienteEstancia.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        this.colNombreCasetaEstancia.setCellValueFactory(new PropertyValueFactory<>("nombreCaseta"));
    }

    /*___________________________________________________________________________________________________________________________________________________________________________*/
    //Prepara los filtros para buscar en las tablas.
    public void iniciaFiltros() {

        //Almacena los datos de cada producto en las Arrays impidiendo duplicados.
        for (int i = 0; i < ListaProductos.size(); i++) {

            IDProdFiltros.add(ListaProductos.get(i).getIDProducto());

            if (!nombresProdFiltros.contains(ListaProductos.get(i).getNombre_Producto())) {
                nombresProdFiltros.add(ListaProductos.get(i).getNombre_Producto());
            }

            if (!proveedorProdFiltros.contains(ListaProductos.get(i).getNombreProveedor())) {
                proveedorProdFiltros.add(ListaProductos.get(i).getNombreProveedor());
            }

            if (!tipoProdFiltros.contains(ListaProductos.get(i).getTipo_Producto())) {
                tipoProdFiltros.add(ListaProductos.get(i).getTipo_Producto());
            }

            if (!cantidadProdFiltros.contains(ListaProductos.get(i).getCantidad())) {
                cantidadProdFiltros.add(ListaProductos.get(i).getCantidad());
            }
        }

        //Almacena los datos de cada proveedor en las Arrays impidiendo duplicados.
        for (int i = 0; i < ListaProveedor.size(); i++) {

            IDProvFiltros.add(ListaProveedor.get(i).getIDProveedor());

            if (!nombresProvFiltros.contains(ListaProveedor.get(i).getNombre_Proveedor())) {
                nombresProvFiltros.add(ListaProveedor.get(i).getNombre_Proveedor());
            }

            if (!telefonoProvFiltros.contains(ListaProveedor.get(i).getTelefono_Proveedor())) {
                telefonoProvFiltros.add(ListaProveedor.get(i).getTelefono_Proveedor());
            }

            if (!paisProvFiltros.contains(ListaProveedor.get(i).getPais())) {
                paisProvFiltros.add(ListaProveedor.get(i).getPais());
            }
        }

        //Almacena los datos de cada Perro en las Arrays impidiendo duplicados.
        for (int i = 0; i < ListaPerros.size(); i++) {

            IDPerroFiltros.add(ListaPerros.get(i).getIDPerro());

            if (!nombresPerroFiltros.contains(ListaPerros.get(i).getNombre_Perro())) {
                nombresPerroFiltros.add(ListaPerros.get(i).getNombre_Perro());
            }

            if (!razasPerroFiltros.contains(ListaPerros.get(i).getRaza())) {
                razasPerroFiltros.add(ListaPerros.get(i).getRaza());
            }

            if (!edadPerroFiltros.contains(ListaPerros.get(i).getEdad())) {
                edadPerroFiltros.add(ListaPerros.get(i).getEdad());
            }

            cartillasPerroFiltros.add(ListaPerros.get(i).getNum_Cartilla());
        }

        //Almacena los datos de cada cliente en las Arrays impidiendo duplicados.
        for (int i = 0; i < ListaClientes.size(); i++) {

            IDClienteFiltros.add(ListaClientes.get(i).getIDCliente());

            DNIClienteFiltros.add(ListaClientes.get(i).getDNI());

            if (!nombresClienteFiltros.contains(ListaClientes.get(i).getNombre_Cliente())) {
                nombresClienteFiltros.add(ListaClientes.get(i).getNombre_Cliente());
            }

            if (!apellidosClienteFiltros.contains(ListaClientes.get(i).getApellidos_Cliente())) {
                apellidosClienteFiltros.add(ListaClientes.get(i).getApellidos_Cliente());
            }

            if (!telefono1ClienteFiltros.contains(ListaClientes.get(i).getTelefono_Cliente1())) {
                telefono1ClienteFiltros.add(ListaClientes.get(i).getTelefono_Cliente1());
            }

            if (!telefono2ClienteFiltros.contains(ListaClientes.get(i).getTelefono_Cliente2())) {
                telefono2ClienteFiltros.add(ListaClientes.get(i).getTelefono_Cliente2());
            }

            if (!tipoPagoClienteFiltros.contains(ListaClientes.get(i).getTipoPago())) {
                tipoPagoClienteFiltros.add(ListaClientes.get(i).getTipoPago());
            }

            emailClienteFiltros.add(ListaClientes.get(i).getEmail_Cliente());
        }

        //Almacena los datos de cada estancia en las Arrays impidiendo duplicados.
        for (int i = 0; i < ListaEstancias.size(); i++) {

            IDEstanciaFiltros.add(ListaEstancias.get(i).getIDEstancia());

            if (!fechaIngresoEstanciaFiltros.contains(ListaEstancias.get(i).getFecha_Ingreso())) {
                fechaIngresoEstanciaFiltros.add(ListaEstancias.get(i).getFecha_Ingreso());
            }

            if (!fechaSalidaEstanciaFiltros.contains(ListaEstancias.get(i).getFecha_Salida())) {
                fechaSalidaEstanciaFiltros.add(ListaEstancias.get(i).getFecha_Salida());
            }

            if (!nomPerroEstanciaFiltros.contains(ListaEstancias.get(i).getNombrePerro())) {
                nomPerroEstanciaFiltros.add(ListaEstancias.get(i).getNombrePerro());
            }

            if (!nomClienteEstanciaFiltros.contains(ListaEstancias.get(i).getNombreCliente())) {
                nomClienteEstanciaFiltros.add(ListaEstancias.get(i).getNombreCliente());
            }

            if (!nomCasetaEstanciaFiltros.contains(ListaEstancias.get(i).getNombreCaseta())) {
                nomCasetaEstanciaFiltros.add(ListaEstancias.get(i).getNombreCaseta());
            }
        }

        //Almacena los nombres de cada Caseta.
        for (int i = 0; i < ListaCasetas.size(); i++) {
            nombresCasetasFiltros.add(ListaCasetas.get(i).getNombre_Caseta());
        }

        //Almacena los cargos existentes.
        for (int i = 0; i < ListaEmpleados.size(); i++) {
            if (!cargosExistentes.contains(ListaEmpleados.get(i).getCargo())) {
                cargosExistentes.add(ListaEmpleados.get(i).getCargo());
            }
        }

        //Introduce los elementos para autocompletar en los elementos respectivos.
        /*---Productos-----------------------------------------------------------*/
        TextFields.bindAutoCompletion(textFiltroIDProd, IDProdFiltros);
        TextFields.bindAutoCompletion(textFiltroNombreProd, nombresProdFiltros);
        TextFields.bindAutoCompletion(textFiltroProveedor, proveedorProdFiltros);
        TextFields.bindAutoCompletion(textFiltroTipo, tipoProdFiltros);
        TextFields.bindAutoCompletion(textFiltroCantidad, cantidadProdFiltros);
        /*-----------------------------------------------------------------------*/

        /*---Proveedores--------------------------------------------------------*/
        TextFields.bindAutoCompletion(textFiltroIDProv, IDProvFiltros);
        TextFields.bindAutoCompletion(textFiltroNombreProv, nombresProvFiltros);
        TextFields.bindAutoCompletion(textFiltroTelefono, telefonoProvFiltros);
        TextFields.bindAutoCompletion(textFiltroPais, paisProvFiltros);
        /*----------------------------------------------------------------------*/

        /*---Perros------------------------------------------------------------------*/
        TextFields.bindAutoCompletion(textFiltroIDPerro, IDPerroFiltros);
        TextFields.bindAutoCompletion(textFiltroNombrePerro, nombresPerroFiltros);
        TextFields.bindAutoCompletion(textFiltroRazaPerro, razasPerroFiltros);
        TextFields.bindAutoCompletion(textFiltroEdadPerro, edadPerroFiltros);
        TextFields.bindAutoCompletion(textFiltroCartillaPerro, cartillasPerroFiltros);
        /*--------------------------------------------------------------------------*/

        /*---Clientes----------------------------------------------------------------------*/
        TextFields.bindAutoCompletion(textFiltroIDCliente, IDClienteFiltros);
        TextFields.bindAutoCompletion(textFiltroNombreCliente, nombresClienteFiltros);
        TextFields.bindAutoCompletion(textFiltroApellidosCliente, apellidosClienteFiltros);
        TextFields.bindAutoCompletion(textFiltroTel1Cliente, telefono1ClienteFiltros);
        TextFields.bindAutoCompletion(textFiltroTel2Cliente, telefono2ClienteFiltros);
        TextFields.bindAutoCompletion(textFiltroEmailCliente, emailClienteFiltros);
        TextFields.bindAutoCompletion(textFiltroTipoPagoCliente, tipoPagoClienteFiltros);
        /*----------------------------------------------------------------------------------*/

        /*---Estancias-------------------------------------------------------------------------*/
        TextFields.bindAutoCompletion(textFiltroIDEstancia, IDEstanciaFiltros);
        TextFields.bindAutoCompletion(textFiltroIngresoEstancia, fechaIngresoEstanciaFiltros);
        TextFields.bindAutoCompletion(textFiltroSalidaEstancia, fechaSalidaEstanciaFiltros);
        TextFields.bindAutoCompletion(textFiltroNomPerroEstancia, nomPerroEstanciaFiltros);
        TextFields.bindAutoCompletion(textFiltroNomClienteEstancia, nomClienteEstanciaFiltros);
        TextFields.bindAutoCompletion(textFiltroNomCasetaEstancia, nomCasetaEstanciaFiltros);
        /*-------------------------------------------------------------------------------------*/

        //Establece Listener a cada TextField destinado a funcionar como un filtrador.
        /*Filtros Productos*/
        textFiltroIDProd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroNombreProd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroProveedor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroTipo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroCantidad.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });
        /*******************/

        /*Filtros Proveedor*/
        textFiltroIDProv.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroNombreProv.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroTelefono.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroPais.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });
        /*******************/

        /*Filtros Perro*/
        textFiltroIDPerro.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroNombrePerro.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroRazaPerro.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroEdadPerro.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroCartillaPerro.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });
        /**************/

        /*Filtros Cliente*/
        textFiltroIDCliente.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroNombreCliente.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroApellidosCliente.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroTel1Cliente.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroTel2Cliente.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroEmailCliente.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroTipoPagoCliente.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });
        /*****************/

        /*Filtros Estancia*/
        textFiltroIDEstancia.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroIngresoEstancia.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroSalidaEstancia.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroNomPerroEstancia.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroNomClienteEstancia.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });

        textFiltroNomCasetaEstancia.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtrar();
            }
        });
        /******************/
    }

    //Se encarga de filtrar las tablas con los datos escritos en los TextFields.
    public void filtrar() {
        try {
            if (tabsAlmacen.isVisible()) {

                if (tabProductos.isSelected()) {
                    listaFiltrosProd.clear();

                    for (int i = 0; i < ListaProductos.size(); i++) {

                        if (String.valueOf(ListaProductos.get(i).getIDProducto()).contains(textFiltroIDProd.getText()) &&
                                ListaProductos.get(i).getNombre_Producto().toLowerCase().contains(textFiltroNombreProd.getText().toLowerCase()) &&
                                ListaProductos.get(i).getTipo_Producto().toLowerCase().contains(textFiltroTipo.getText().toLowerCase()) &&
                                String.valueOf(ListaProductos.get(i).getCantidad()).contains(textFiltroCantidad.getText()) &&
                                ListaProductos.get(i).getNombreProveedor().toLowerCase().contains(textFiltroProveedor.getText().toLowerCase())
                        ) {
                            listaFiltrosProd.add(ListaProductos.get(i));
                        }
                    }

                    tablaProductos.setItems(listaFiltrosProd);

                } else if (tabProveedores.isSelected()) {
                    listaFiltrosProv.clear();

                    for (int i = 0; i < ListaProveedor.size(); i++) {

                        if (String.valueOf(ListaProveedor.get(i).getIDProveedor()).contains(textFiltroIDProv.getText()) &&
                                ListaProveedor.get(i).getNombre_Proveedor().toLowerCase().contains(textFiltroNombreProv.getText().toLowerCase()) &&
                                ListaProveedor.get(i).getTelefono_Proveedor().toLowerCase().contains(textFiltroTelefono.getText().toLowerCase()) &&
                                ListaProveedor.get(i).getPais().toLowerCase().contains(textFiltroPais.getText().toLowerCase())
                        ) {
                            listaFiltrosProv.add(ListaProveedor.get(i));
                        }
                    }

                    tablaProveedor.setItems(listaFiltrosProv);
                }

            } else if (tabsFacturacion.isVisible()) {

                if (tabPerros.isSelected()) {
                    listaFiltrosPerros.clear();

                    for (int i = 0; i < ListaPerros.size(); i++) {

                        if (String.valueOf(ListaPerros.get(i).getIDPerro()).contains(textFiltroIDPerro.getText()) &&
                                ListaPerros.get(i).getNombre_Perro().toLowerCase().contains(textFiltroNombrePerro.getText().toLowerCase()) &&
                                ListaPerros.get(i).getRaza().toLowerCase().contains(textFiltroRazaPerro.getText().toLowerCase()) &&
                                String.valueOf(ListaPerros.get(i).getEdad()).contains(textFiltroEdadPerro.getText().toLowerCase()) &&
                                String.valueOf(ListaPerros.get(i).getNum_Cartilla()).contains(textFiltroCartillaPerro.getText().toLowerCase())
                        ) {
                            listaFiltrosPerros.add(ListaPerros.get(i));
                        }
                    }

                    tablaPerros.setItems(listaFiltrosPerros);

                } else if (tabCliente.isSelected()) {
                    listaFiltrosClientes.clear();

                    for (int i = 0; i < ListaClientes.size(); i++) {

                        if (String.valueOf(ListaClientes.get(i).getIDCliente()).contains(textFiltroIDCliente.getText()) &&
                                ListaClientes.get(i).getNombre_Cliente().toLowerCase().contains(textFiltroNombreCliente.getText().toLowerCase()) &&
                                ListaClientes.get(i).getApellidos_Cliente().toLowerCase().contains(textFiltroApellidosCliente.getText().toLowerCase()) &&
                                ListaClientes.get(i).getTelefono_Cliente1().contains(textFiltroTel1Cliente.getText().toLowerCase()) &&
                                ListaClientes.get(i).getTelefono_Cliente2().contains(textFiltroTel2Cliente.getText().toLowerCase()) &&
                                ListaClientes.get(i).getEmail_Cliente().contains(textFiltroEmailCliente.getText().toLowerCase()) &&
                                ListaClientes.get(i).getTipoPago().contains(textFiltroTipoPagoCliente.getText().toLowerCase())
                        ) {
                            listaFiltrosClientes.add(ListaClientes.get(i));
                        }
                    }

                    tablaClientes.setItems(listaFiltrosClientes);

                } else if (tabEstancia.isSelected()) {
                    listaFiltrosEstancia.clear();

                    for (int i = 0; i < ListaEstancias.size(); i++) {

                        if (String.valueOf(ListaEstancias.get(i).getIDEstancia()).contains(textFiltroIDEstancia.getText()) &&
                                String.valueOf(ListaEstancias.get(i).getFecha_Ingreso()).toLowerCase().contains(textFiltroIngresoEstancia.getText().toLowerCase()) &&
                                String.valueOf(ListaEstancias.get(i).getFecha_Salida()).toLowerCase().contains(textFiltroSalidaEstancia.getText().toLowerCase()) &&
                                ListaEstancias.get(i).getNombrePerro().contains(textFiltroNomPerroEstancia.getText().toLowerCase()) &&
                                ListaEstancias.get(i).getNombreCliente().contains(textFiltroNomClienteEstancia.getText().toLowerCase()) &&
                                ListaEstancias.get(i).getNombreCaseta().contains(textFiltroNomCasetaEstancia.getText().toLowerCase())
                        ) {
                            listaFiltrosEstancia.add(ListaEstancias.get(i));
                        }
                    }

                    tablaEstancia.setItems(listaFiltrosEstancia);
                }
            }
        } catch (NullPointerException e) {
        }
    }

    /*___________________________________________________________________________________________________________________________________________________________________________*/
    //Rellena las ListViews con los respectivos Empleados.
    public void iniciaListView() {

        //Listas para almacenar los elementos de las ListViews.
        List<CustomListView> list1 = new ArrayList<>();
        List<CustomListView> list2 = new ArrayList<>();

        Image iconEmple; //Icono para los Emepleados.

        //Recorre la lista de empleados.
        for (int i = 0; i < ListaEmpleados.size(); i++) {

            //Establece la imegen del Empleado.
            iconEmple = new Image(new File("ImgEmpleados/" + ListaEmpleados.get(i).getNombreCompleto().replace(" ", "_") + ".png").toURI().toString());

            //Si no tiene una imagen assignada se le pone una por defecto.
            if (iconEmple.getHeight() == 0.0) {
                iconEmple = new Image("/ImgEmpleados/Null.png");
            }

            //Añade al Empleado a la lista 1.
            list1.add(new CustomListView(iconEmple, ListaEmpleados.get(i).getNombre_Empleado(), ListaEmpleados.get(i).getApellidos_Empleado(), ListaEmpleados.get(i).getCargo()));

            //Si el eEmpleado trabaja hoy se añadirá también a la lista 2.
            if (trabajaHoy(ListaEmpleados.get(i).getHorario_Trabajo())) {
                list2.add(new CustomListView(iconEmple, ListaEmpleados.get(i).getNombre_Empleado(), ListaEmpleados.get(i).getApellidos_Empleado(), ListaEmpleados.get(i).getCargo()));
            }
        }

        //Transforma las Listas en ObservableList.
        ObservableList<CustomListView> myObservableList1 = FXCollections.observableList(list1);
        ObservableList<CustomListView> myObservableList2 = FXCollections.observableList(list2);

        //Mete los elementos de las listas en las ListViews.
        listViewEmpleados.setItems(myObservableList1);
        listViewEmpleadosActivos.setItems(myObservableList2);
        listViewEmpleadosActivos.setFixedCellSize(60); //Establece el tamaño máximo de las celdas de las ListViews.

        //Cambia el fondo de los empleados de la lista 2 según su Turno.
        for (int i = 0; i < listViewEmpleadosActivos.getItems().size(); i++) {

            String nombre = listViewEmpleadosActivos.getItems().get(i).getNombreCompleto();

            for (int j = 0; j < ListaEmpleados.size(); j++) {

                if (ListaEmpleados.get(j).getNombreCompleto().equals(nombre)) {

                    switch (ListaEmpleados.get(j).getTurno()) {
                        case "Diurno":
                            listViewEmpleadosActivos.getItems().get(i).getStyleClass().add("empleadoDia");
                            break;

                        case "Nocturno":
                            listViewEmpleadosActivos.getItems().get(i).getStyleClass().add("empleadoNoche");
                            break;

                        case "Partido":
                            listViewEmpleadosActivos.getItems().get(i).getStyleClass().add("empleadoPartido");
                            break;
                    }
                    break;
                }
            }
        }
    }

    //Mustra los datos del Empleado seleccionado en las ListViews en sus respectivos elementos.
    public void seleccionarEmpleado() {

        JFXListView<CustomListView> seleccionada = null; //Almacenará al empleado seleccionado.

        //Controla la ListView en la que se haya clicado.
        if (listViewEmpleados.isFocused()) {
            seleccionada = listViewEmpleados;

        } else if (listViewEmpleadosActivos.isFocused()) {
            seleccionada = listViewEmpleadosActivos;
        }

        //Recorre la lista de Empleados para encontrar el seleccionado y mostrar sus datos.
        for (int i = 0; i < ListaEmpleados.size(); i++) {

            if (ListaEmpleados.get(i).getNombreCompleto().equals(seleccionada.getSelectionModel().getSelectedItem().getNombreCompleto())) {
                try {

                    tarjetaEmpleNom.setText(ListaEmpleados.get(i).getNombre_Empleado());
                    tarjetaEmpleApe.setText(ListaEmpleados.get(i).getApellidos_Empleado());
                    tarjetaEmpleDNI.setText(ListaEmpleados.get(i).getDNI_Empleado());
                    tarjetaEmpleTel.setText(ListaEmpleados.get(i).getTelefono_Empleado());
                    tarjetaEmpleEmail.setText(ListaEmpleados.get(i).getEmail_Empleado());
                    tarjetaEmpleCargo.setText(ListaEmpleados.get(i).getCargo());
                    tarjetaEmpleHorario.setText(ListaEmpleados.get(i).getHorario_Trabajo());
                    tarjetaEmpleTurno.setText(ListaEmpleados.get(i).getTurno());
                    tarjetaEmpleCaseta.setText(ListaEmpleados.get(i).getNombreCaseta());
                    tarjetaEmpleImgCaseta.setImage(new Image(new File("ImgCasetas/" + ListaEmpleados.get(i).getNombreCaseta().replace(" ", "_") + ".png").toURI().toString()));
                    tarjetaEmpleImg.setImage(new Image(new File("ImgEmpleados/" + seleccionada.getSelectionModel().getSelectedItem().getNombreCompleto().replace(" ", "_") + ".png").toURI().toString()));
                    break;

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*___________________________________________________________________________________________________________________________________________________________________________*/
    //Controla las acciones que realizará cada botón de Añadir, Modificar y Eliminar.
    public void accionesCRUD() throws SQLException, IOException, ParseException {
        if (botonAnadir.isFocused()) {

            if (tabCaseta.isSelected()) return;

            FXMLLoader loader = null;
            String tituoVentana = null;

            //Según el módulo y la pestaña seleccionada, se abrirá una ventana u otra.
            switch (menuModulos.getValue()) {
                case "Almacén":
                    if (tabProductos.isSelected()) {
                        loader = new FXMLLoader(getClass().getResource("/Vista/VentanaCamposProductos.fxml"));
                        tituoVentana = "Añadir Producto";
                    } else if (tabProveedores.isSelected()) {
                        loader = new FXMLLoader(getClass().getResource("/Vista/VentanaCamposProveedores.fxml"));
                        tituoVentana = "Añadir Proveedor";
                    }
                    break;

                case "Facturación":
                    if (tabPerros.isSelected()) {
                        loader = new FXMLLoader(getClass().getResource("/Vista/VentanaCamposPerros.fxml"));
                        tituoVentana = "Añadir Perro";
                    } else if (tabCliente.isSelected()) {
                        loader = new FXMLLoader(getClass().getResource("/Vista/VentanaCamposCliente.fxml"));
                        tituoVentana = "Añadir Cliente";
                    } else if (tabEstancia.isSelected()) {
                        loader = new FXMLLoader(getClass().getResource("/Vista/VentanaCamposEstancia.fxml"));
                        tituoVentana = "Añadir Estancia";
                    }
                    break;
            }

            Parent root = loader.load();

            VentanaCamposController controlador = loader.getController();

            //Llama a métodos para establecer el autocompletado de los campos en la ventanas.
            if (menuModulos.getValue().equals("Almacén") && tabProductos.isSelected()) {
                controlador.iniciaFieldProv(nombresProvFiltros);

            } else if (menuModulos.getValue().equals("Facturación") && tabEstancia.isSelected()) {
                controlador.iniciaFieldEstancia(DNIClienteFiltros, cartillasPerroFiltros, nombresCasetasFiltros);
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/Imagenes/iconoSolo.png"));
            stage.setTitle(tituoVentana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        } else if (botonAnadirEmple.isFocused()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/VentanaCamposEmpleados.fxml"));
            Parent root = loader.load();

            VentanaCamposController controlador = loader.getController();

            controlador.iniciaFieldEmpleados(cargosExistentes, nombresCasetasFiltros);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/Imagenes/iconoSolo.png"));
            stage.setTitle("Añadir Empleado");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        } else if (botonEliminar.isFocused() || botonEliminarEmple.isFocused()) {

            String tabla = null, columnas = null;

            //Según el módulo y la pestaña seleccionada, se eliminará un elemento u otra.
            if (tabsAlmacen.isVisible() && tabProductos.isSelected()) {
                ObservableList<Producto> producto;
                producto = tablaProductos.getSelectionModel().getSelectedItems();
                tabla = "PRODUCTOS";
                columnas = "IDProducto = " + producto.get(0).getIDProducto();

            } else if (tabsAlmacen.isVisible() && tabProveedores.isSelected()) {
                ObservableList<Proveedor> proveedor;
                proveedor = tablaProveedor.getSelectionModel().getSelectedItems();
                tabla = "PROVEEDOR";
                columnas = "IDProveedor = " + proveedor.get(0).getIDProveedor();

            } else if (tarjetaEmplePanel.isVisible()) {
                tabla = "EMPLEADOS";
                columnas = "Nombre_Empleado = '" + tarjetaEmpleNom.getText() + "' AND Apellidos_Empleado = '" + tarjetaEmpleApe.getText() + "'";

            } else if (tabsFacturacion.isVisible() && tabPerros.isSelected()) {
                ObservableList<Perro> Perro;
                Perro = tablaPerros.getSelectionModel().getSelectedItems();
                tabla = "PerroS";
                columnas = "IDPerro = " + Perro.get(0).getIDPerro();

            } else if (tabsFacturacion.isVisible() && tabCliente.isSelected()) {
                ObservableList<Cliente> cliente;
                cliente = tablaClientes.getSelectionModel().getSelectedItems();
                tabla = "CLIENTES";
                columnas = "IDCliente = " + cliente.get(0).getIDCliente();

            } else if (tabsFacturacion.isVisible() && tabEstancia.isSelected()) {
                ObservableList<Estancia> estancia;
                estancia = tablaEstancia.getSelectionModel().getSelectedItems();
                tabla = "ESTANCIA";
                columnas = "IDEstancia = " + estancia.get(0).getIDEstancia();
            }

            //Realiza la consulta para eliminar.
            this.IO.actualizaRegistros("DELETE FROM " + tabla + " WHERE " + columnas);

        } else if (botonModificar.isFocused()) {

            FXMLLoader loader = null;
            String tituoVentana = null;

            //Según el módulo y la pestaña seleccionada, se abrirá una ventana u otra.
            if (menuModulos.getValue().equals("Almacén") && tabProductos.isSelected()) {
                loader = new FXMLLoader(getClass().getResource("/Vista/VentanaCamposProductos.fxml"));
                tituoVentana = "Modificar Producto";

            } else if (menuModulos.getValue().equals("Almacén") && tabProveedores.isSelected()) {
                loader = new FXMLLoader(getClass().getResource("/Vista/VentanaCamposProveedores.fxml"));
                tituoVentana = "Modificar Proveedor";

            } else if (menuModulos.getValue().equals("Facturación") && tabPerros.isSelected()) {
                loader = new FXMLLoader(getClass().getResource("/Vista/VentanaCamposPerros.fxml"));
                tituoVentana = "Modificar Perro";

            } else if (menuModulos.getValue().equals("Facturación") && tabCliente.isSelected()) {
                loader = new FXMLLoader(getClass().getResource("/Vista/VentanaCamposCliente.fxml"));
                tituoVentana = "Modificar Cliente";

            } else if (menuModulos.getValue().equals("Facturación") && tabEstancia.isSelected()) {
                loader = new FXMLLoader(getClass().getResource("/Vista/VentanaCamposEstancia.fxml"));
                tituoVentana = "Modificar Estancia";
            }

            Parent root = loader.load();

            VentanaCamposController controlador = loader.getController();

            //Llama a métodos para establecer el autocompletado de los campos en la ventanas
            // e rellenar los campos con los datos del elemento a modificar.
            if (menuModulos.getValue().equals("Almacén") && tabProductos.isSelected()) {
                controlador.iniciarCampos(tablaProductos.getSelectionModel().getSelectedItem());
                controlador.iniciaFieldProv(nombresProvFiltros);

            } else if (menuModulos.getValue().equals("Almacén") && tabProveedores.isSelected()) {
                controlador.iniciarCampos(tablaProveedor.getSelectionModel().getSelectedItem());

            } else if (menuModulos.getValue().equals("Facturación") && tabPerros.isSelected()) {
                controlador.iniciarCampos(tablaPerros.getSelectionModel().getSelectedItem());

            } else if (menuModulos.getValue().equals("Facturación") && tabCliente.isSelected()) {
                controlador.iniciarCampos(tablaClientes.getSelectionModel().getSelectedItem());

            } else if (menuModulos.getValue().equals("Facturación") && tabEstancia.isSelected()) {
                controlador.iniciarCampos(tablaEstancia.getSelectionModel().getSelectedItem());
                controlador.iniciaFieldEstancia(DNIClienteFiltros, cartillasPerroFiltros, nombresCasetasFiltros);
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/Imagenes/iconoSolo.png"));
            stage.setTitle(tituoVentana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        } else if (botonModificarEmple.isFocused()) {
            JFXListView<CustomListView> seleccionada = null; //Almacenará al empleado seleccionado.

            //Controla la ListView en la que se haya clicado.
            if (listViewEmpleados.isFocused()) {
                seleccionada = listViewEmpleados;

            } else if (listViewEmpleadosActivos.isFocused()) {
                seleccionada = listViewEmpleadosActivos;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/VentanaCamposEmpleados.fxml"));
            Parent root = loader.load();

            VentanaCamposController controlador = loader.getController();

            controlador.iniciaFieldEmpleados(cargosExistentes, nombresCasetasFiltros);

            //Recorre la lista de Empleados para encontrar el seleccionado y mostrar sus datos.
            for (int i = 0; i < ListaEmpleados.size(); i++) {

                if (ListaEmpleados.get(i).getNombreCompleto().equals(tarjetaEmpleNom.getText() + " " + tarjetaEmpleApe.getText())) {
                    try {

                        Empleado encontrado = ListaEmpleados.get(i);
                        controlador.iniciarCampos(encontrado);
                        break;

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/Imagenes/iconoSolo.png"));
            stage.setTitle("Modificar Empleado");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        } else if (botonLimpiarFiltros.isFocused()){
            if (tabsAlmacen.isVisible()){
                if (tabProductos.isSelected()){
                    textFiltroIDProd.clear();
                    textFiltroNombreProd.clear();
                    textFiltroTipo.clear();
                    textFiltroCantidad.clear();
                    textFiltroProveedor.clear();

                } else if (tabProveedores.isSelected()){
                    textFiltroIDProv.clear();
                    textFiltroNombreProv.clear();
                    textFiltroTelefono.clear();
                    textFiltroPais.clear();
                }

            } else if (tabsFacturacion.isVisible()){
                if (tabPerros.isSelected()){
                    textFiltroIDPerro.clear();
                    textFiltroNombrePerro.clear();
                    textFiltroRazaPerro.clear();
                    textFiltroEdadPerro.clear();
                    textFiltroCartillaPerro.clear();

                } else if (tabCliente.isSelected()){
                    textFiltroIDCliente.clear();
                    textFiltroNombreCliente.clear();
                    textFiltroApellidosCliente.clear();
                    textFiltroTel1Cliente.clear();
                    textFiltroTel2Cliente.clear();
                    textFiltroEmailCliente.clear();
                    textFiltroTipoPagoCliente.clear();

                } else if (tabEstancia.isSelected()){
                    textFiltroIDEstancia.clear();
                    textFiltroIngresoEstancia.clear();
                    textFiltroSalidaEstancia.clear();
                    textFiltroNomPerroEstancia.clear();
                    textFiltroNomClienteEstancia.clear();
                    textFiltroNomCasetaEstancia.clear();
                }
            }

        }else if (reporteEstancia.isFocused()){
            //Almacena la estancia seleccionada en la tabla.
            ObservableList<Estancia> Seleccionado;
            Seleccionado = (ObservableList<Estancia>) tablaEstancia.getSelectionModel().getSelectedItems();

            //HashMap para guarda los parámetros que usará el reporte.
            HashMap<String, Object> parametros = new HashMap<String, Object>();

            //Inserta los parámetros.
            parametros.put("ParIDUsuario", Seleccionado.get(0).getIDCliente());
            parametros.put("ParFechaIng",Seleccionado.get(0).getFecha_Ingreso());

            JasperReport reporte;

            String archivo = "cashbill_chome_A4.jasper";

            try {
                //Crea el reporte y lo muestra.

                Class.forName("org.mariadb.jdbc.Driver");
                String urlCon = "jdbc:mariadb://localhost:3306/castillocanino";
                Connection conexBD = DriverManager.getConnection(urlCon, "root", "root");
                JasperReport reporE = JasperCompileManager
                        .compileReport("cashbill_chome_A4.jasper");
                JasperPrint rp = JasperFillManager.fillReport(reporE, null, conexBD);
                JasperExportManager.exportReportToPdfFile(rp, "ReportePDF.pdf");
                /*
                reporte = (JasperReport) JRLoader.loadObjectFromFile(archivo);

                JasperPrint print = JasperFillManager.fillReport(reporte, parametros, IO.conexion() );
                JasperExportManager.exportReportToPdfFile(print,"Factura-" + java.time.LocalDate.now() + "-" + Seleccionado.get(0).getNombreCliente() +".pdf");

                JasperViewer.viewReport(print, false);
*/
            } catch (JRException jRException) {
                System.out.println(jRException.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
/*
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                String urlCon = "jdbc:mariadb://localhost:3306/castillocanino";
                Connection conexBD = DriverManager.getConnection(urlCon, "root", "root");


                JasperReport reporE = JasperCompileManager.compileReport("C:/Users/damda/OneDrive/Escritorio/kamoNduck/cashbill_chome_A4.jasper");
                JasperPrint rp = JasperFillManager.fillReport(reporE, null, conexBD);
                JasperExportManager.exportReportToPdfFile(rp, "Factura-" + java.time.LocalDate.now() + "-" + Seleccionado.get(0).getNombreCliente() +".pdf");

            }catch(Exception e) {
                e.printStackTrace();
            }
            */
        }

        iniciaTodo(); //Reinicia los datos del programa.
    }

    //Método para crear un reporte de pedido en caso de que la cantidad de existencias del producdo
    // sea menor que el mínimo que debería haber.
    public void reportePedirProd(int ID, String nomProducto){

        //HashMap para guarda los parámetros que usará el reporte.
        HashMap<String, Object> parametros = new HashMap<String, Object>();

        //Inserta los parámetros.
        parametros.put("ParIDProducto",ID);

        JasperReport reporte;

        String archivo = "Orden de Compra CastilloCanino.jasper";

        try {
            //Crea el reporte y lo muestra.
            reporte = (JasperReport) JRLoader.loadObjectFromFile(archivo);

            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, IO.conexion() );
            JasperExportManager.exportReportToPdfFile(print,"Orden Compra-" + java.time.LocalDate.now() + "-" + nomProducto + ".pdf");

            JasperViewer.viewReport(print, false);

        } catch (JRException jRException) {
            System.out.println(jRException.getMessage());
        }
    }

    /*___________________________________________________________________________________________________________________________________________________________________________*/
    //Establece ene el ComboBox el módulo elegido en la ventana anterior.
    public void moduloElegido(String boton) {
        this.menuModulos.setValue(boton);
    }

    //Esconde o muestra elementos según se cambie de módulos.
    public void cambiaModulo() throws IOException {
        switch (menuModulos.getValue()) {
            case "Almacén":
                tabsAlmacen.setVisible(true);
                panelFiltros.setVisible(true);
                labelAlmacen.setVisible(true);
                miniLabelAlmacen.setVisible(true);
                lineaAlamacen.setVisible(true);
                labelNomProdProv.setVisible(true);
                imgAlmacen.setVisible(true);
                botonModificar.setVisible(true);
                botonEliminar.setVisible(true);
                botonAnadir.setVisible(true);

                tabsFacturacion.setVisible(false);

                labelNoModifiEmpleados.setVisible(false);
                labelNombreEmpleado.setVisible(false);
                listViewEmpleados.setVisible(false);
                listViewEmpleadosActivos.setVisible(false);
                rectanguloEmpleados.setVisible(false);
                tarjetaTituloEmple.setVisible(false);
                tarjetaEmplePanel.setVisible(false);

                reporteEstancia.setVisible(false);

                escuchaTabs();
                break;

            case "Facturación":
                tabsAlmacen.setVisible(false);
                panelFiltros.setVisible(true);
                labelAlmacen.setVisible(true);
                miniLabelAlmacen.setVisible(true);
                lineaAlamacen.setVisible(true);
                labelNomProdProv.setVisible(true);
                imgAlmacen.setVisible(true);
                botonModificar.setVisible(true);
                botonEliminar.setVisible(true);
                botonAnadir.setVisible(true);

                tabsFacturacion.setVisible(true);

                labelNoModifiEmpleados.setVisible(false);
                labelNombreEmpleado.setVisible(false);
                listViewEmpleados.setVisible(false);
                listViewEmpleadosActivos.setVisible(false);
                rectanguloEmpleados.setVisible(false);
                tarjetaTituloEmple.setVisible(false);
                tarjetaEmplePanel.setVisible(false);

                escuchaTabs();
                break;

            case "Empleados":
                tabsAlmacen.setVisible(false);
                panelFiltros.setVisible(false);
                labelAlmacen.setVisible(false);
                miniLabelAlmacen.setVisible(false);
                lineaAlamacen.setVisible(false);
                labelNomProdProv.setVisible(false);
                imgAlmacen.setVisible(false);
                botonModificar.setVisible(false);
                botonEliminar.setVisible(false);
                botonAnadir.setVisible(false);

                tabsFacturacion.setVisible(false);

                labelNoModifiEmpleados.setVisible(true);
                labelNombreEmpleado.setVisible(true);
                listViewEmpleados.setVisible(true);
                listViewEmpleadosActivos.setVisible(true);
                rectanguloEmpleados.setVisible(true);
                tarjetaTituloEmple.setVisible(true);
                tarjetaEmplePanel.setVisible(true);

                labelNomPerro.setVisible(false);
                labelNomCliente.setVisible(false);
                labelNomCaseta.setVisible(false);

                imagePerro.setVisible(false);
                imageCaseta.setVisible(false);
                imageCliente.setVisible(false);

                reporteEstancia.setVisible(false);
                break;

            case "Atrás":
                volverABienvenida();
                break;
        }
    }

    //Cierra la ventana actual (Ventana Principal) y vuelve a la anterior (Ventana bienvenida).
    public void volverABienvenida() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/VentanaBienvenida.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.getIcons().add(new Image("/Imagenes/iconoSolo.png"));
        stage.setTitle("Empresa CastilloCanino");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();

        Stage myStage = (Stage) this.botonModificar.getScene().getWindow();
        myStage.close();
    }

    /*___________________________________________________________________________________________________________________________________________________________________________*/
    //Se encarga de mostrar la información de los elemenotos seleccionados en las tablas.
    public void escuchaTablas() throws SQLException {

        if (tablaProductos.isFocused()) {
            //Almacena el producto seleccionado.
            ObservableList<Producto> Seleccionado;
            Seleccionado = (ObservableList<Producto>) tablaProductos.getSelectionModel().getSelectedItems();

            labelNomProdProv.setText(Seleccionado.get(0).getNombre_Producto());

            //Muetra su imagen asignada en la ImageView.
            imgAlmacen.setImage(new Image(new File("ImgProductos/" + Seleccionado.get(0).getNombre_Producto().replace(" ", "_") + ".png").toURI().toString()));

            //En caso de no tener pone una imagen por defecto.
            if (imgAlmacen.getImage().isError()) {
                imgAlmacen.setImage(new Image("/Imagenes/iconoSolo.png"));
            }

        } else if (tablaProveedor.isFocused()) {
            //Almacena al proveedor seleccionado.
            ObservableList<Proveedor> Seleccionado;
            Seleccionado = (ObservableList<Proveedor>) tablaProveedor.getSelectionModel().getSelectedItems();

            labelNomProdProv.setText(Seleccionado.get(0).getNombre_Proveedor());

            //Muetra su imagen asignada en la ImageView.
            imgAlmacen.setImage(new Image(new File("ImgProveedores/" + Seleccionado.get(0).getNombre_Proveedor().replace(" ", "_") + ".png").toURI().toString()));

            //En caso de no tener pone una imagen por defecto.
            if (imgAlmacen.getImage().isError()) {
                imgAlmacen.setImage(new Image("/Imagenes/iconoSolo.png"));
            }

        } else if (tablaPerros.isFocused()) {
            //Almacena al Perro seleccionado.
            ObservableList<Perro> Seleccionado;
            Seleccionado = (ObservableList<Perro>) tablaPerros.getSelectionModel().getSelectedItems();

            labelNomProdProv.setText(Seleccionado.get(0).getNombre_Perro());

            //Muetra su imagen asignada en la ImageView.
            imgAlmacen.setImage(new Image(new File("ImgPerros/" + (Seleccionado.get(0).getNombre_Perro() + " " + Seleccionado.get(0).getNum_Cartilla()).replace(" ", "_") + ".png").toURI().toString()));

            //En caso de no tener pone una imagen por defecto.
            if (imgAlmacen.getImage().isError()) {
                imgAlmacen.setImage(new Image("/Imagenes/iconoSolo.png"));
            }

        } else if (tablaClientes.isFocused()) {
            //Almacena al cliente seleccionado.
            ObservableList<Cliente> Seleccionado;
            Seleccionado = (ObservableList<Cliente>) tablaClientes.getSelectionModel().getSelectedItems();

            labelNomProdProv.setText(Seleccionado.get(0).getNombre_Cliente() + " " + Seleccionado.get(0).getApellidos_Cliente());

            //Muetra su imagen asignada en la ImageView.
            imgAlmacen.setImage(new Image(new File("ImgClientes/" + (Seleccionado.get(0).getNombre_Cliente() + " " + Seleccionado.get(0).getApellidos_Cliente()).replace(" ", "_") + ".png").toURI().toString()));

            //En caso de no tener pone una imagen por defecto.
            if (imgAlmacen.getImage().isError()) {
                imgAlmacen.setImage(new Image("/Imagenes/iconoSolo.png"));
            }

        } else if (tablaCasetas.isFocused()) {
            //Almacena al Caseta seleccionado.
            ObservableList<Caseta> Seleccionado;
            Seleccionado = (ObservableList<Caseta>) tablaCasetas.getSelectionModel().getSelectedItems();

            labelNomProdProv.setText(Seleccionado.get(0).getNombre_Caseta());

            //Muetra su imagen asignada en la ImageView.
            imgAlmacen.setImage(new Image(new File("ImgCasetas/" + Seleccionado.get(0).getNombre_Caseta().replace(" ", "_") + ".png").toURI().toString()));

            //En caso de no tener pone una imagen por defecto.
            if (imgAlmacen.getImage().isError()) {
                imgAlmacen.setImage(new Image("/Imagenes/iconoSolo.png"));
            }
        } else if (tablaEstancia.isFocused()) {
            //Almacena a la estancia seleccionada.
            ObservableList<Estancia> Seleccionado;
            Seleccionado = (ObservableList<Estancia>) tablaEstancia.getSelectionModel().getSelectedItems();

            labelNomPerro.setText(Seleccionado.get(0).getNombrePerro());
            labelNomCliente.setText(Seleccionado.get(0).getNombreCliente());
            labelNomCaseta.setText(Seleccionado.get(0).getNombreCaseta());

            //Muetra sus imagenes asignadas en las ImageView.
            imagePerro.setImage(new Image(new File("ImgPerros/" + (Seleccionado.get(0).getNombrePerro() + " " + String.valueOf(Seleccionado.get(0).getNumCartilla())).replace(" ", "_") + ".png").toURI().toString()));
            imageCaseta.setImage(new Image(new File("ImgCasetas/" + Seleccionado.get(0).getNombreCaseta().replace(" ", "_") + ".png").toURI().toString()));
            imageCliente.setImage(new Image(new File("ImgClientes/" + Seleccionado.get(0).getNombreCliente().replace(" ", "_") + ".png").toURI().toString()));

            //En caso de no tener pone unas imagenes por defecto.
            if (imagePerro.getImage().isError()) {
                imagePerro.setImage(new Image("/Imagenes/iconoSolo.png"));
            }
            if (imageCaseta.getImage().isError()) {
                imageCaseta.setImage(new Image("/Imagenes/iconoSolo.png"));
            }
            if (imageCliente.getImage().isError()) {
                imageCliente.setImage(new Image("/Imagenes/iconoSolo.png"));
            }
        }
    }

    //Se encarga de cambiar los filtros y otros elementos según de se cambie de pestaña.
    public void escuchaTabs() {
        try {
            if (tabsAlmacen.isVisible()) {
                if (tabProductos.isSelected()) {

                    panelFiltros.setVisible(true);
                    botonAnadir.setVisible(true);
                    botonEliminar.setVisible(true);
                    botonModificar.setVisible(true);

                    mostrarFiltrosProd(true);
                    mostrarFiltrosProv(false);
                    mostrarFiltrosPerro(false);
                    mostrarFiltrosCliente(false);
                    mostrarFiltrosEstancia(false);

                    labelNomPerro.setVisible(false);
                    labelNomCliente.setVisible(false);
                    labelNomCaseta.setVisible(false);

                    imagePerro.setVisible(false);
                    imageCaseta.setVisible(false);
                    imageCliente.setVisible(false);

                    labelNomProdProv.setVisible(true);
                    imgAlmacen.setVisible(true);

                    labelAlmacen.setText("ALMACÉN");
                    labelNomProdProv.setText("CASTILLO CANINO");
                    imgAlmacen.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imagePerro.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imageCaseta.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imageCliente.setImage(new Image("/Imagenes/iconoSolo.png"));

                } else if (tabProveedores.isSelected()) {

                    panelFiltros.setVisible(true);
                    botonAnadir.setVisible(true);
                    botonEliminar.setVisible(true);
                    botonModificar.setVisible(true);

                    mostrarFiltrosProd(false);
                    mostrarFiltrosProv(true);
                    mostrarFiltrosPerro(false);
                    mostrarFiltrosCliente(false);
                    mostrarFiltrosEstancia(false);

                    labelNomPerro.setVisible(false);
                    labelNomCliente.setVisible(false);
                    labelNomCaseta.setVisible(false);

                    imagePerro.setVisible(false);
                    imageCaseta.setVisible(false);
                    imageCliente.setVisible(false);

                    labelNomProdProv.setVisible(true);
                    imgAlmacen.setVisible(true);

                    labelAlmacen.setText("ALMACÉN");
                    labelNomProdProv.setText("CASTILLO CANINO");
                    imgAlmacen.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imagePerro.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imageCaseta.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imageCliente.setImage(new Image("/Imagenes/iconoSolo.png"));

                }
            } else if (tabsFacturacion.isVisible()) {
                if (tabPerros.isSelected()) {

                    panelFiltros.setVisible(true);
                    botonAnadir.setVisible(true);
                    botonEliminar.setVisible(true);
                    botonModificar.setVisible(true);
                    reporteEstancia.setVisible(false);

                    mostrarFiltrosProd(false);
                    mostrarFiltrosProv(false);
                    mostrarFiltrosPerro(true);
                    mostrarFiltrosCliente(false);
                    mostrarFiltrosEstancia(false);

                    labelNomPerro.setVisible(false);
                    labelNomCliente.setVisible(false);
                    labelNomCaseta.setVisible(false);

                    imagePerro.setVisible(false);
                    imageCaseta.setVisible(false);
                    imageCliente.setVisible(false);

                    labelNomProdProv.setVisible(true);
                    imgAlmacen.setVisible(true);

                    labelAlmacen.setText("FACTURACIÓN");
                    labelNomProdProv.setText("CASTILLO CANINO");

                    imgAlmacen.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imagePerro.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imageCaseta.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imageCliente.setImage(new Image("/Imagenes/iconoSolo.png"));

                    labelNomPerro.setText("");
                    labelNomCliente.setText("");
                    labelNomCaseta.setText("");

                } else if (tabCliente.isSelected()) {

                    panelFiltros.setVisible(true);
                    botonAnadir.setVisible(true);
                    botonEliminar.setVisible(true);
                    botonModificar.setVisible(true);
                    reporteEstancia.setVisible(false);

                    mostrarFiltrosProd(false);
                    mostrarFiltrosProv(false);
                    mostrarFiltrosPerro(false);
                    mostrarFiltrosCliente(true);
                    mostrarFiltrosEstancia(false);

                    labelNomPerro.setVisible(false);
                    labelNomCliente.setVisible(false);
                    labelNomCaseta.setVisible(false);

                    imagePerro.setVisible(false);
                    imageCaseta.setVisible(false);
                    imageCliente.setVisible(false);

                    labelNomProdProv.setVisible(true);
                    imgAlmacen.setVisible(true);

                    labelAlmacen.setText("FACTURACIÓN");
                    labelNomProdProv.setText("CASTILLO CANINO");

                    imgAlmacen.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imagePerro.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imageCaseta.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imageCliente.setImage(new Image("/Imagenes/iconoSolo.png"));

                    labelNomPerro.setText("");
                    labelNomCliente.setText("");
                    labelNomCaseta.setText("");

                } else if (tabCaseta.isSelected()) {

                    panelFiltros.setVisible(false);
                    botonAnadir.setVisible(false);
                    botonEliminar.setVisible(false);
                    botonModificar.setVisible(false);
                    reporteEstancia.setVisible(false);

                    labelNomPerro.setVisible(false);
                    labelNomCliente.setVisible(false);
                    labelNomCaseta.setVisible(false);

                    imagePerro.setVisible(false);
                    imageCaseta.setVisible(false);
                    imageCliente.setVisible(false);

                    labelNomProdProv.setVisible(true);
                    imgAlmacen.setVisible(true);

                    labelAlmacen.setText("FACTURACIÓN");
                    labelNomProdProv.setText("CASTILLO CANINO");

                    imgAlmacen.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imagePerro.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imageCaseta.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imageCliente.setImage(new Image("/Imagenes/iconoSolo.png"));

                    labelNomPerro.setText("");
                    labelNomCliente.setText("");
                    labelNomCaseta.setText("");

                } else if (tabEstancia.isSelected()) {

                    panelFiltros.setVisible(true);
                    botonAnadir.setVisible(true);
                    botonEliminar.setVisible(true);
                    botonModificar.setVisible(true);
                    reporteEstancia.setVisible(true);

                    mostrarFiltrosProd(false);
                    mostrarFiltrosProv(false);
                    mostrarFiltrosPerro(false);
                    mostrarFiltrosCliente(false);
                    mostrarFiltrosEstancia(true);

                    labelNomPerro.setVisible(true);
                    labelNomCliente.setVisible(true);
                    labelNomCaseta.setVisible(true);

                    imagePerro.setVisible(true);
                    imageCaseta.setVisible(true);
                    imageCliente.setVisible(true);

                    labelNomProdProv.setVisible(false);
                    imgAlmacen.setVisible(false);

                    labelAlmacen.setText("FACTURACIÓN");
                    labelNomProdProv.setText("CASTILLO CANINO");

                    imgAlmacen.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imagePerro.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imageCaseta.setImage(new Image("/Imagenes/iconoSolo.png"));
                    imageCliente.setImage(new Image("/Imagenes/iconoSolo.png"));
                }
            }
        } catch (NullPointerException e) {
        }
    }

    /*___________________________________________________________________________________________________________________________________________________________________________*/
    //Métodos para mostrar/ocultar los filtros.
    public void mostrarFiltrosProd(boolean sino){
        textFiltroIDProd.setVisible(sino);
        textFiltroNombreProd.setVisible(sino);
        textFiltroTipo.setVisible(sino);
        textFiltroCantidad.setVisible(sino);
        textFiltroProveedor.setVisible(sino);
    }

    public void mostrarFiltrosProv(boolean sino){
        textFiltroIDProv.setVisible(sino);
        textFiltroNombreProv.setVisible(sino);
        textFiltroTelefono.setVisible(sino);
        textFiltroPais.setVisible(sino);
    }

    public void mostrarFiltrosPerro(boolean sino){
        textFiltroIDPerro.setVisible(sino);
        textFiltroNombrePerro.setVisible(sino);
        textFiltroRazaPerro.setVisible(sino);
        textFiltroEdadPerro.setVisible(sino);
        textFiltroCartillaPerro.setVisible(sino);
    }

    public void mostrarFiltrosCliente(boolean sino){
        textFiltroIDCliente.setVisible(sino);
        textFiltroNombreCliente.setVisible(sino);
        textFiltroApellidosCliente.setVisible(sino);
        textFiltroTel1Cliente.setVisible(sino);
        textFiltroTel2Cliente.setVisible(sino);
        textFiltroEmailCliente.setVisible(sino);
        textFiltroTipoPagoCliente.setVisible(sino);
    }

    public void mostrarFiltrosEstancia(boolean sino){
        textFiltroIDEstancia.setVisible(sino);
        textFiltroIngresoEstancia.setVisible(sino);
        textFiltroSalidaEstancia.setVisible(sino);
        textFiltroNomPerroEstancia.setVisible(sino);
        textFiltroNomClienteEstancia.setVisible(sino);
        textFiltroNomCasetaEstancia.setVisible(sino);
    }

    /*___________________________________________________________________________________________________________________________________________________________________________*/
    //Se usa para comprobar si un trabajador esta trabajando hoy o no.
    public boolean trabajaHoy(String horario) {
        String diaActul = null;
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.MONDAY:
                diaActul = "L";
                break;
            case Calendar.TUESDAY:
                diaActul = "M";
                break;
            case Calendar.WEDNESDAY:
                diaActul = "X";
                break;
            case Calendar.THURSDAY:
                diaActul = "J";
                break;
            case Calendar.FRIDAY:
                diaActul = "V";
                break;
            case Calendar.SATURDAY:
                diaActul = "S";
                break;
            case Calendar.SUNDAY:
                diaActul = "D";
                break;
        }

        if (horario.contains(diaActul)) {
            return true;
        } else {
            return false;
        }
    }
    /*___________________________________________________________________________________________________________________________________________________________________________*/
}