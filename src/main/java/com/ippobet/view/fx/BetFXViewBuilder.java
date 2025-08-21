package com.ippobet.view.fx;

import com.ippobet.controller.BetController;
import com.ippobet.model.Bet;
import com.ippobet.view.BetView;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import javafx.util.converter.DoubleStringConverter;

public class BetFXViewBuilder implements Builder<Region>, BetView {
    private final TableView<Bet> table;
    private final BetController betController;


    public BetFXViewBuilder(TableView<Bet> table, BetController betController) {
        this.table = table;
        this.betController = betController;
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

        table.getItems().addAll(betController.getAllBets());

        VBox layout = new VBox(10, table);
        layout.setPadding(new Insets(10));

        return layout;
    }
}
