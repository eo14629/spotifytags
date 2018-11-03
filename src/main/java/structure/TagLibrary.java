/*
 *A SET of all the Tags such that repeats do not occur.
 * This needs to be initialised at the start of the main program.
 *
 * Some way of loading it each time the program launches from the DB?
 */

package structure;

import actions.ConnectDb;

import java.sql.*;
import java.util.*;

public class TagLibrary {
    private Connection c;
    // set for the names as this is the key feature of the tags which
    // needs to be unique.
    private Set<String> tagNames;
    private List<Tag> tags;

    private TagLibrary(String test) {}
    public TagLibrary() {
         tagNames = new HashSet<String>();
         c = new ConnectDb("spotify").getConn();
    }

    public boolean addTags(Tag... newTags) {
        // query db to check whether a new tag can be entered - depending on the db
        // either add or dont add and respond with the appropriate messages.
        boolean success = true;
        String insertion = "INSERT INTO Tag (Name) VALUES (?)";

        for (Tag tag: newTags) {
            try(PreparedStatement s = c.prepareStatement(insertion)) {
                s.setString(1, tag.getName().toLowerCase());
                c.setAutoCommit(false);
                int rows_affected = s.executeUpdate();
                if (rows_affected == 1) {
                    c.commit();
                } else {
                    success = false;
                }
            } catch(SQLException e) {
                try {
                    c.rollback();
                } catch (SQLException f) {
                    System.err.println("f: " + f.getMessage());
                }
                System.err.println("e: " + e.getMessage() + "  code:" + e.getErrorCode());
                if (e.getErrorCode() == 1062) {// MAGIC NUMBER
                    success = false;
                }
            }
        }

        return success;
    }

    public List<Tag> getAllTags() {
        // will probs have to query the db in order to get this info.
        return tags;
    }

    public static void main(String[] args) {
        TagLibrary program = new TagLibrary("test");
        program.test();
    }

    private void claim(boolean b) { if (!b) throw new Error("Test failure"); }

    private void test() {
        TagLibrary tLib = new TagLibrary();

        Tag chill = new Tag("chill");
        Tag Chill = new Tag("Chill");
        claim(tLib.addTags(chill));
        claim(! tLib.addTags(chill));
        claim(! tLib.addTags(Chill));
    }
}
