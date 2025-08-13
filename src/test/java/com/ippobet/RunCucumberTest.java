package com.ippobet;

import com.ippobet.model.Bet;
import com.ippobet.repository.mongo.BetMongoRepository;
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
import javafx.scene.control.TableView;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.osgi.service.TestFx;

import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.ippobet")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/bdd")
public class RunCucumberTest extends ApplicationTest
{
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:8.0.12"));
    private static final String DB_NAME = "bet";
    private static final String COLLECTION_NAME = "event";
    private MongoClient mongoClient;
    private MongoCollection<Document> betCollection;
    private List<Bet> betsInDB;
    
    static {
        System.setProperty("testfx.robot", "glass");
        //System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
        mongoDBContainer.start();
        try
        {
            ApplicationTest.launch(App.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception
    {
        // Questo metodo deve essere chiamato poiché internalBefore è registrato solamente
        // agli hook di JUnit e non a quelli di cucumber.
        // Ref: https://github.com/TestFX/TestFX/issues/635
        internalBefore();

        betsInDB = new ArrayList<>();

        mongoClient = MongoClients.create(RunCucumberTest.mongoDBContainer.getConnectionString());
        mongoClient.getDatabase(DB_NAME).drop();
        mongoClient.getDatabase(DB_NAME).createCollection(COLLECTION_NAME);

        betCollection = mongoClient.getDatabase(DB_NAME).getCollection(COLLECTION_NAME);
    }

    @After
    public void tearDown() throws Exception
    {
        // Questo metodo deve essere chiamato poiché internalBefore è registrato solamente
        // agli hook di JUnit e non a quelli di cucumber.
        // Ref: https://github.com/TestFX/TestFX/issues/635
        internalAfter();

        mongoClient.close();
    }

    @Given("{int} bets in the Database")
    public void givenNBetsInDB(int betCount) {
        var toAdd = new ArrayList<Document>();

        for(int i = 0; i < betCount; i++) {
            Document doc = (new Document())
                .append(BetMongoRepository.HOME_TEAM_ATTR, "home team " + i)
                .append(BetMongoRepository.AWAY_TEAM_ATTR, "away team " + i)
                .append(BetMongoRepository.OUTCOME_ATTR, "outcome " + i)
                .append(BetMongoRepository.ODD_ATTR, 1);

            toAdd.add(doc);
            Bet bet = new Bet(
                doc.getString(BetMongoRepository.HOME_TEAM_ATTR),
                doc.getString(BetMongoRepository.AWAY_TEAM_ATTR),
                doc.getString(BetMongoRepository.OUTCOME_ATTR),
                doc.getInteger(BetMongoRepository.ODD_ATTR));
            betsInDB.add(bet);
        }

        betCollection.insertMany(toAdd);
    }

    @When("show all bets")
    public void whenShowAllBets() throws InterruptedException
    {
        TableView<Bet> tableView = lookup("#" + App.BETS_TABLE_ID).query();
        tableView.setItems(FXCollections.observableList(betsInDB));
        tableView.refresh();

        // Wait for JavaFX thread to process the update
        Thread.sleep(3000);
    }

    @Then("bets are correctly displayed")
    public void betsAreCorrectlyDisplayed()
    {
        TableView<Bet> query = lookup("#" + App.BETS_TABLE_ID).query();
        Assertions.assertEquals(betsInDB, query.getItems().stream().toList());
    }


    @Given("test given")
    public void testGiven()
    {
    }
}
