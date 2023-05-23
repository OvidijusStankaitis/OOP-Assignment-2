package LoanLogic;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public interface LoanTable<T> {
    TableView<T> getTable();

    void setData(ObservableList<T> data);

    ObservableList<T> getData();
}
