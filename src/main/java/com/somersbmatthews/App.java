package com.somersbmatthews;

import java.util.List;
import java.util.Map;

import com.somersbmatthews.sqlmakers.PostgresMaker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App {
    private static Logger log = LogManager.getLogger(App.class);
    private static Database db;

    public static void main(String[] args) {
        setUpDb();
        runDemo();

    }

    private static void setUpDb() {
        Database datab = new Database();
        datab.setSqlMaker(new PostgresMaker());
        datab.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        datab.setUser("postgres");
        datab.setPassword("postgres");
        db = datab;

    }

    private static void runDemo() {
        db.sql("drop table if exists pirate").execute();
        db.createTable(Pirate.class);

        Pirate pirate = new Pirate(1, "Blackbeard", false);

        db.insert(pirate);

        Pirate foundPirate = db.where("name=?", "Blackbeard").first(Pirate.class);

        log.warn(foundPirate.toString());

        Pirate updatedPirate = new Pirate(1, "Blackbeard", true);

        db.update(updatedPirate); // .where("id=?", 4);

        // int rowsAffected = db.update(pirate).getRowsAffected();
        // int rowsAffected = db.sql("UPDATE pirate SET isatsea=true WHERE
        // id=4").getRowsAffected();

        // db.table("pirate").where("id=?", 4).update(updatedPirate);

        Pirate foundPirateAtSea = db.where("name=?", "Blackbeard").first(Pirate.class);

        log.warn(foundPirateAtSea);

        // db.table("pirate").where("id=?", 1).delete();
        // db.delete(pirateToBeDeleted);

        List<Pirate> pirates = db.where("name=?", "Blackbeard").results(Pirate.class);

        if (pirates.size() == 0) {
            log.warn("there are no pirates in the database");
        } else {
            log.warn("pirate deleting failed");
        }

        // Pirate foundPirateAtSea2 = db.where("name=?",
        // "Blackbeard").first(Pirate.class);

        // log.warn(foundPirateAtSea2);
    }

}
