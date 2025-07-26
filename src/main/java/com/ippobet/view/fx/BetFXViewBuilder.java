package com.ippobet.view.fx;

import com.ippobet.model.Bet;
import com.ippobet.view.BetView;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Builder;
import javafx.util.converter.DoubleStringConverter;

public class BetFXViewBuilder implements Builder<Region>, BetView
{
    private final TableView<Bet> table;


    public BetFXViewBuilder(TableView<Bet> table)
    {
        this.table = table;
    }


    @Override
    public void updateBets(List<Bet> bets)
    {
        table.setItems(FXCollections.observableList(bets));
    }

    @Override
    public Region build()
    {
        TableColumn<Bet, String> homeTeamCol = new TableColumn<>("Home team");
        homeTeamCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHomeTeam()));
        homeTeamCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Bet, String> awayTeamCol = new TableColumn<>("Away team");
        awayTeamCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAwayTeam()));
        awayTeamCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Bet, String> outcomeCol = new TableColumn<>("Outcome");
        outcomeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOutcome()));
        outcomeCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Bet, Double> oddCol = new TableColumn<>("Odd");
        oddCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getOdd()).asObject());
        oddCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        table.getColumns().addAll(homeTeamCol, awayTeamCol, outcomeCol, oddCol);

        VBox layout = new VBox(10, table);
        layout.setPadding(new Insets(10));

        return layout;
    }
}
