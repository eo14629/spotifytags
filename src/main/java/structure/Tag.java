/*
 * A Tag is simply a string which can be associated with any number of Songs
 */

package structure;

public class Tag {
    private String name;

    public Tag (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
