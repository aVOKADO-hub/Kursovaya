package org.example.demo3;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PanelController implements Initializable {
    private final int FILE_TYPE_COLUMN_SIZE = 50;
    private final int FILE_NAME_COLUMN_SIZE = 240;
    private final int FILE_SIZE_COLUMN_SIZE = 120;
    private final int FILE_DATE_COLUMN_SIZE = 180;
    @FXML
    private TableView<FileInfo> filesTable;
    @FXML
    private ComboBox<String> disksBox;
    @FXML
    private TextField pathField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>("Type");
        fileTypeColumn.setCellValueFactory(type-> new SimpleStringProperty(type.getValue().getType().getName()));
        fileTypeColumn.setPrefWidth(FILE_TYPE_COLUMN_SIZE);
        TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("Name");
        fileNameColumn.setCellValueFactory(fileName -> new SimpleStringProperty(fileName.getValue().getFileName()));
        fileNameColumn.setPrefWidth(FILE_NAME_COLUMN_SIZE);

        TableColumn<FileInfo,Long> fileSizeColumn = new TableColumn<>("Size");
        fileSizeColumn.setCellValueFactory(size->new SimpleObjectProperty<>(size.getValue().getSize()));
        fileSizeColumn.setCellFactory(column->{
            return new TableCell<FileInfo,Long>(){
                @Override
                protected void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null||empty){
                        setText(null);
                        setStyle("");
                    }else{
                        String text = String.format("%,d bytes",item);
                        if (item==-1L){
                            text = "[DIR]";
                        }
                        setText(text);
                    }
                }
            };
        });
        fileSizeColumn.setPrefWidth(FILE_SIZE_COLUMN_SIZE);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        TableColumn<FileInfo,String > fileDateColumn = new TableColumn<>("Date of modification");
        fileDateColumn.setCellValueFactory(date->new SimpleStringProperty(date.getValue().getLastModified().format(dtf)));
        fileDateColumn.setPrefWidth(FILE_DATE_COLUMN_SIZE);

        filesTable.getColumns().addAll(fileTypeColumn,fileNameColumn,fileSizeColumn,fileDateColumn);
        filesTable.getSortOrder().add(fileTypeColumn);

        disksBox.getItems().clear();
        for (Path p: FileSystems.getDefault().getRootDirectories()){
            disksBox.getItems().add(p.toString());
        }
        disksBox.getSelectionModel().select(0);

        filesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2){
                    Path path = Paths.get(pathField.getText()).resolve(filesTable.getSelectionModel().getSelectedItem().getFileName());
                    if (Files.isDirectory(path)){
                        updateList(path);
                    }
                }
            }
        });

        updateList(Paths.get("."));
    }
    public void updateList(Path path){
        try {
            pathField.setText(path.normalize().toAbsolutePath().toString());
            filesTable.getItems().clear();
            filesTable.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            filesTable.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Can`t update  files list", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void btnPathUpAction() {
        Path upperPath = Paths.get(pathField.getText()).getParent();
        if (upperPath!=null){
            updateList(upperPath);
        }
    }

    public void selectDiskAction(ActionEvent actionEvent) {
        ComboBox<String > element =(ComboBox<String>) actionEvent.getSource();
        updateList(Paths.get(element.getSelectionModel().getSelectedItem()));
    }
    public String getSelectedFileName(){
        if (!filesTable.isFocused()){
            return null;
        }
        return filesTable.getSelectionModel().getSelectedItem().getFileName();
    }
    public String getCurrentPath(){
        return pathField.getText();
    }
}
