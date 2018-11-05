package sample.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import sample.model.Book;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Controller implements Initializable {
    @FXML
    private TableView tableView;

    private ObservableList data;

    private Text actionStatus;

    @FXML
    private Scene scene;
    @FXML
    private Label lblBookTitle,lblAuthor;
    @FXML
    private TextField txtTitle,txtAuthor,fieldAra;
    @FXML
    private TableColumn<Book,String> columnTitle,columnAuthor;
    private TableColumn<Book, LocalDate> columnDate;
    @FXML
    private Button btnNew,btnSave,btnUpdate,btnDelete;
    @FXML
    private DatePicker dtpDate;
  //  private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
//    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
//            DATE_PATTERN);

     private StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
         @Override
         public String toString(LocalDate date) {
             if (date != null) {
                 return dateFormatter.format(date);
             } else {
                 return "";
             }
         }

         @Override
         public LocalDate fromString(String string) {
             if (string != null && !string.isEmpty()) {
                 return LocalDate.parse(string, dateFormatter);
             } else {
                 return null;
             }
         }



     };

private ObservableList getInitialTableData(){

    List list=new ArrayList();

    list.add(new Book(txtTitle.getText(),txtAuthor.getText(), Date.valueOf(dtpDate.getValue())));

    ObservableList data = FXCollections.observableList(list);
    return data;
}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableView.setEditable(true);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        columnTitle = new TableColumn("Title");
        columnAuthor= new TableColumn("Author");
        columnDate=new TableColumn("Datum");
        columnTitle.setCellValueFactory(new PropertyValueFactory("title"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory("author"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Book,LocalDate>("date"));
        columnTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        columnAuthor.setCellFactory(TextFieldTableCell.forTableColumn());
        tableView.getColumns().setAll(columnTitle,columnAuthor,columnDate);

        registerEvents();

    }



    public void initShortcuts() {
        if (scene != null) {
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F9), () -> sceneExit());
        }
    }

    private void sceneExit() {
        Platform.exit();
    }

    private void registerEvents() {


        btnNew.setOnAction(new AddButtonListener());
        btnSave.setOnAction(new SaveButtonListener());
        btnDelete.setOnAction(new DeleteButtonListener());
        btnUpdate.setOnAction(new UpdateButtonListener());


    }


    private class UpdateCellEventListener implements EventHandler<CellEditEvent<Book, String>>{

        @Override
        public void handle(CellEditEvent t) {
            ((Book) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTitle((String) t.getNewValue());
            ((Book) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAuthor((String) t.getNewValue());

        }
    };


    private class AddButtonListener implements EventHandler {


        @Override
        public void handle(Event event) {
            txtTitle.setText("");
            txtAuthor.setText("");

        }
        }

        private class SaveButtonListener implements EventHandler{


            @Override
            public void handle(Event event) {


                List list=new ArrayList();
                list.add(new Book(txtTitle.getText(),txtAuthor.getText(), Date.valueOf(dtpDate.getValue())));
                ObservableList data = FXCollections.observableList(list);
                tableView.setItems(data);
                System.out.println(dtpDate.getValue());
            }
        }

        private class DeleteButtonListener implements EventHandler {

            @Override
            public void handle(Event event) {

            }
        }

    private class UpdateButtonListener implements EventHandler {


        @Override
        public void handle(Event event) {

            columnTitle.setOnEditCommit(new UpdateCellEventListener());
            columnAuthor.setOnEditCommit(new UpdateCellEventListener());

            TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            TableColumn col = pos.getTableColumn();
            String data1 = (String) col.getCellObservableValue(row).getValue();
            //cell
            Book row1 = (Book) tableView.getSelectionModel().getSelectedItem();
            String c1 = row1.getAuthor();
            String c2=row1.getTitle();
            String d=String.valueOf(row1.getDatum());
            System.out.println(c1+" "+c2+" "+d);



        }
    }


    }




