package metodoyajuste;

import generador.Archivo;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import mensajes.Mensajes;
import pruebas.pruebas.ChiCuadrada;

/**
 * @author dzp
 */
public class VentanaMAController implements Initializable {

    @FXML    private TableView<String[]> tabla;
    @FXML    private TextField entradaN;
    @FXML    private Button GA;
    @FXML    private TextArea resultado;
    
    Archivo archivo;
    double numeros[];
    ChiCuadrada chiCuadrada;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        archivo = new Archivo("numerosAleatorios.bin");
    }    
    
    private void mostrarTabla()
    {
        String[][] matrizNumeros;
        
        tabla.getColumns().clear();
        if(numeros != null){
            matrizNumeros = new String[(int)Math.ceil(numeros.length/6.0)+1][6];

            for(int i=0;i<6;i++)
                matrizNumeros[0][i] = (i+1)+""; 
            for(int i=0;i<(int)Math.ceil(numeros.length/6.0);i++){
                for(int j=0;j<6;j++){
                    if(numeros.length>i*6+j)
                        matrizNumeros[i+1][j] = ""+numeros[i*6+j];
                }
            }

            ObservableList<String[]> data = FXCollections.observableArrayList();
            data.addAll(Arrays.asList(matrizNumeros));
            data.remove(0);

            for (int i = 0; i < matrizNumeros[0].length; i++) {
                TableColumn tc = new TableColumn(matrizNumeros[0][i]);
                final int colNo = i;
                tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                        return new SimpleStringProperty((p.getValue()[colNo]));
                    }
                });
                tc.setPrefWidth(56);
                tabla.getColumns().add(tc);
            }
            tabla.setItems(data);
        }
        //else
        //    mensajeError("Error", archivo.errorMensaje);
        
        if(archivo.error)
            Mensajes.mensajeError("Error", archivo.errorMensaje);
    }

    @FXML
    private void cargarNumeros(ActionEvent event) {
        String nT;
        int n;
        
        nT = entradaN.getText();
        
        try{
            n = Integer.parseInt(nT);
            
            if(n >= 1)
            {
                numeros = archivo.leer(n);
                mostrarTabla();
                
            }
            else
                Mensajes.mensajeError("Error", "Introduce solo números enteros mayores o igual a 1");
        }catch(NumberFormatException  e){
            Mensajes.mensajeError("Error", "Introduce solo números enteros mayores o igual a 1");
        }
    }

    @FXML
    private void generarYAjustar(ActionEvent event) {
    }

    @FXML
    private void guardar(ActionEvent event) {
    }

    @FXML
    private void verDetalles(ActionEvent event) {
    }
}
