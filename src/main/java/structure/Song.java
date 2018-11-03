/*
 *
 * Class wraps up the Song information provided by Spotify
 * Also contains information about which Tags are associated with the song
 *
 */

package structure;

import java.util.*;

// TODO

// Associated DB queries

public class Song {
    private String title;
    private Set<Tag> assocTags;

    public Song(String title) { this.title = title; }
    public Song(String title, Tag... newAssocTags) {
        this.title = title;
        assocTags = new HashSet<Tag>();
        Collections.addAll(Arrays.asList(newAssocTags));
    }

//    public boolean addTags()

    public String getTitle() { return title; }

    public Set<Tag> getAssocTags() { return assocTags; }
}
