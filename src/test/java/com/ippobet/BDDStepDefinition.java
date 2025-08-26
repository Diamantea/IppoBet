package com.ippobet;

import com.ippobet.model.Bet;
import com.ippobet.repository.mongo.BetMongoRepository;
import com.ippobet.view.fx.BetFXViewBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
import org.testfx.framework.junit5.ApplicationTest;

public class BDDStepDefinition extends ApplicationTest
{
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:8.0.12"));
    private static final String DB_NAME = "bet";
    private static final String COLLECTION_NAME = "event";
    private MongoClient mongoClient;
    private MongoCollection<Document> betCollection;
    private List<Bet> betsToAddInDB;
    private List<Bet> expected;

    static
    {
        System.setProperty("testfx.robot", "glass");
        //System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
        mongoDBContainer.start();
        try
        {
            ApplicationTest.launch(App.class, "--dbPort=" + mongoDBContainer.getFirstMappedPort());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp()
    {
        betsToAddInDB = new ArrayList<>();
        expected = new ArrayList<>();

        mongoClient = MongoClients.create(mongoDBContainer.getConnectionString());
        mongoClient.getDatabase(DB_NAME).drop();
        mongoClient.getDatabase(DB_NAME).createCollection(COLLECTION_NAME);

        betCollection = mongoClient.getDatabase(DB_NAME).getCollection(COLLECTION_NAME);
    }


    @After
    public void tearDown()
    {
        mongoClient.close();
    }


    @Given("{int} bets in the Database")
    public void givenNBetsInDB(int betCount)
    {
        var toAdd = new ArrayList<Document>();

        for (int i = 0; i < betCount; i++)
        {
            Document doc = (new Document())
                .append(BetMongoRepository.HOME_TEAM_ATTR, "home team " + i)
                .append(BetMongoRepository.AWAY_TEAM_ATTR, "away team " + i)
                .append(BetMongoRepository.OUTCOME_ATTR, "outcome " + i)
                .append(BetMongoRepository.ODD_ATTR, 1.);

            toAdd.add(doc);
            Bet bet = new Bet(
                doc.getString(BetMongoRepository.HOME_TEAM_ATTR),
                doc.getString(BetMongoRepository.AWAY_TEAM_ATTR),
                doc.getString(BetMongoRepository.OUTCOME_ATTR),
                doc.getDouble(BetMongoRepository.ODD_ATTR));
            betsToAddInDB.add(bet);
            expected.add(bet);
        }

        betCollection.insertMany(toAdd);
    }


    @When("show all bets")
    public void whenShowAllBets() throws InterruptedException
    {
        TableView<Bet> tableView = lookup("#" + App.BETS_TABLE_ID).query();
        tableView.setItems(FXCollections.observableList(betsToAddInDB));
        tableView.refresh();

        // Wait for JavaFX thread to process the update
        Thread.sleep(3000);
    }


    @Then("bets are correctly displayed")
    public void betsAreCorrectlyDisplayed()
    {
        TableView<Bet> query = lookup("#" + App.BETS_TABLE_ID).query();
        var actual = query.getItems().stream().toList();
        Assertions.assertEquals(expected, actual);
    }


    @Given("user provides a new odds in the text field")
    public void userProvidesANewOddsInTheTextField()
    {
        var homeTeamInput = (TextField) lookup("#" + BetFXViewBuilder.HOME_TEAM_INPUT_ID).query();
        var awayTeamInput = (TextField) lookup("#" + BetFXViewBuilder.AWAY_TEAM_INPUT_ID).query();
        var outcomeInput = (TextField) lookup("#" + BetFXViewBuilder.OUTCOME_INPUT_ID).query();
        var oddInput = (TextField) lookup("#" + BetFXViewBuilder.ODD_INPUT_ID).query();

        var bet = new Bet("Lecce", "Monza", "X", 1.42);
        betsToAddInDB.add(bet);
        expected.add(bet);

        homeTeamInput.setText(bet.getHomeTeam());
        awayTeamInput.setText(bet.getAwayTeam());
        outcomeInput.setText(bet.getOutcome());
        oddInput.setText(String.valueOf(bet.getOdd()));
    }


    @When("click the add button")
    public void clickTheAddButton()
    {
        var addButton = (Button) lookup("#" + BetFXViewBuilder.ADD_BUTTON_INPUT_ID).query();
        addButton.fire();
    }
}
