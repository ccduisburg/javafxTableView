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
    private List list=new ArrayList();





private ObservableList getInitialTableData(){

    List list=new ArrayList();

    list.add(new Book(txtTitle.getText(),txtAuthor.getText()));

    data = FXCollections.observableList(list);
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
     //   columnAuthor.setCellFactory(TextFieldTableCell.forTableColumn());
        columnAuthor.setCellFactory(EditCell.forTableColumn());

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
        tableView.setOnKeyPressed(event -> {
        if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {

        } else if (event.getCode() == KeyCode.RIGHT ||  event.getCode() == KeyCode.TAB) {

            tableView.getSelectionModel().selectNext();
            event.consume();
        } else if (event.getCode() == KeyCode.LEFT) {
            selectPrevious();
            event.consume();

        }

    });



        btnNew.setOnAction(new AddButtonListener());
        btnSave.setOnAction(new SaveButtonListener());
        btnDelete.setOnAction(new DeleteButtonListener());
        btnUpdate.setOnAction(new UpdateButtonListener());


    }



    private TableColumn < Book, ? > getTableColumn(

            final TableColumn < Book, ? > column, int offset) {

        int columnIndex = tableView.getVisibleLeafIndex(column);

        int newColumnIndex = columnIndex + offset;

        return tableView.getVisibleLeafColumn(newColumnIndex);

    }

    @SuppressWarnings("unchecked")

    private void selectPrevious() {

        if (tableView.getSelectionModel().isCellSelectionEnabled()) {

            // in cell selection mode, we have to wrap around, going from

            // right-to-left, and then wrapping to the end of the previous line

            TablePosition < Book, ? > pos = tableView.getFocusModel()

                    .getFocusedCell();

            if (pos.getColumn() - 1 >= 0) {

                // go to previous row

                tableView.getSelectionModel().select(pos.getRow(), getTableColumn(pos.getTableColumn(), -1));

            } else if (pos.getRow() < tableView.getItems().size()) {

                // wrap to end of previous row

                tableView.getSelectionModel().select(pos.getRow() - 1,

                        tableView.getVisibleLeafColumn( tableView.getVisibleLeafColumns().size() - 1));

            }

        } else {

            int focusIndex = tableView.getFocusModel().getFocusedIndex();

            if (focusIndex == -1) {

                tableView.getSelectionModel().select(tableView.getItems().size() - 1);

            } else if (focusIndex > 0) {

                tableView.getSelectionModel().select(focusIndex - 1);

            }

        }

    }

    private class UpdateCellEventListener implements EventHandler<CellEditEvent<Book, String>>{

        @Override
        public void handle(CellEditEvent t) {
           // ((Book) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTitle((String) t.getNewValue());
          //  ((Book) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAuthor((String) t.getNewValue());
            final String valueTitle = (String) (t.getNewValue() != null ? t.getNewValue() : t.getOldValue());
            ((Book) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTitle(valueTitle);

            final String value = (String) (t.getNewValue() != null ? t.getNewValue() : t.getOldValue());
            ((Book) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAuthor(value);

            tableView.refresh();

        };

        }



    private class AddButtonListener implements EventHandler {


        @Override
        public void handle(Event event) {
            txtTitle.setText("");
            txtAuthor.setText("");
            list.add(new Book(txtTitle.getText(),txtAuthor.getText()));
            ObservableList data = FXCollections.observableList(list);
            tableView.setItems(data);
        }
        }

        private class SaveButtonListener implements EventHandler{

            @Override
            public void handle(Event event) {

                list.add(new Book(txtTitle.getText(),txtAuthor.getText()));
                ObservableList data = FXCollections.observableList(list);
                tableView.setItems(data);
                clearField();
            }
        }

        private class DeleteButtonListener implements EventHandler {
            @Override
            public void handle(Event event) {

                TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                list.remove(row);
                ObservableList data = FXCollections.observableList(list);
                tableView.setItems(data);

            }
        }

    private class UpdateButtonListener implements EventHandler {
        @Override
        public void handle(Event event) {
            columnTitle.setOnEditCommit(new UpdateCellEventListener());
            columnAuthor.setOnEditCommit(new UpdateCellEventListener());

//            TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
//            int row = pos.getRow();
//            TableColumn col = pos.getTableColumn();
//            String data1 = (String) col.getCellObservableValue(row).getValue();
//            //cell
//            Book row1 = (Book) tableView.getSelectionModel().getSelectedItem();
//            String c1 = row1.getAuthor();
//            String c2=row1.getTitle();
//            String d=String.valueOf(row1.getDatum());
//            System.out.println(c1+" "+c2+" "+d);



        }

    }

    private void clearField(){

        txtTitle.setText("");
        txtAuthor.setText("");
    }


    }




