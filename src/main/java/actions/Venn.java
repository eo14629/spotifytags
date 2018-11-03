package actions;

import structure.Song;
import structure.Tag;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

// TODO
// decide which playlists to create and provide a list of them (basically loads of WHERE clauses)
// Get input from user for a number of Tags providing the necessary error invocations
// then carry out the WHERE clause using that information and provide a list of songs to th user.

// Need to add associated DB queries such that things are changed behind the scene.

// Add TagLibrary functionality

// Test Methods:

// Create loads of Tags and songs and insert them into the db


public class Venn {
    private Connection conn = null;
    private List<Tag> tags = null;
    private Set<String> tagNames = null;

    // only for testing purposes hence private.
    private Venn() {}

    // allowing for new Venns to be made in other classes
    public Venn (Tag... newTags) {
        tags = new ArrayList<Tag>();
        tagNames = new HashSet<String>();
        addTags(newTags);
    }

    public List<Tag> getTags() { return tags; }

    public int numOfTags() {
        return tags.size();
    }

    public boolean containsTag(Tag tag) {
        return tags.contains(tag);
    }

    // returns false if there were some duplicates
    public boolean addTags(Tag... newTags) {
        boolean no_duplicates = true;
        for (Tag tag:newTags) {
            if (tagNames.add(tag.getName().toLowerCase())) {
                tags.add(tag);
                System.out.println("no dupp");
            } else {
                System.out.println("dupp");
                no_duplicates = false;
            }
        }

        for (String tagname: tagNames) {
            System.out.println("tagnames: " + tagname);
        }

        return no_duplicates;
    }

    private void ConnectDb(String URL) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection(URL, "root", "root");
        } catch (ClassNotFoundException ce) {
            System.err.println(ce.getCause().getMessage());
        } catch (SQLException se) {
            System.err.println(se.getCause().getMessage());
        }
    }

    /*
     *
     * TESTS
     *
     */

    public static void main(String[] args) {
        Venn program = new Venn();
        program.test();
    }

    private void claim(boolean b) { if (!b) throw new Error("Test failure"); }

    private void test() {
        System.out.println("Tests Started");

        Tag chill = new Tag("chill");
        Tag beats = new Tag("beats");
        Tag rock = new Tag("rock");

        Venn chill_beats = new Venn(chill, beats);
        Venn chill_rock = new Venn(chill, rock);
        Venn chill_beats_rock = new Venn(chill, beats, rock);

        claim(chill_beats.numOfTags() == 2);
        claim(chill_beats.containsTag(chill));
        claim(chill_beats.containsTag(beats));
        claim(! chill_beats.containsTag(rock));
        claim(chill_beats_rock.numOfTags() == 3);
        claim(chill_beats_rock.containsTag(chill));
        claim(chill_beats_rock.containsTag(beats));
        claim(chill_beats_rock.containsTag(rock));
        claim(chill_beats_rock.addTags(new Tag("Rap")));
        claim(! chill_beats_rock.addTags(new Tag("Rap")));

        System.out.println("Tests Completed");
    }

}
