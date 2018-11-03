//package data.playlists;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Playlists {
    private static final String accessToken = "BQDgzk6_SabowYzNhF_xURMo2rE00T1f_txGTqoFmMBXInW7tyvCyUvtWDMN4qUOv8E1H1U5RsqwaS-Nvt7ft8pD63kmeyG5qB-vG-XYoUIug382oErUhgZpEckI7my3oeNiEK0YemGtx_6B62BtCg94DorcrCTsw40g&refresh_token=AQDsq39FkSkkNgw-dKnTGnqqU7csVU-Bsz8cb3zc53Kh_V945vb-p38PNibNAak2uBAU27vu4ZSHD7YvLeCBksndV3NmyWCJzy3CLwgfGAsbd5VuuEEoseAQ9BTRfHm_nF211A";
    private static final String userId = "dotdotdot123";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest = spotifyApi
            .getListOfUsersPlaylists(userId)
            .limit(10)
            .offset(0)
            .build();

    public static void getListOfUsersPlaylists_Sync() {
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfUsersPlaylistsRequest.execute();

            for (PlaylistSimplified p: playlistSimplifiedPaging.getItems()) {
                System.out.println(p.getName());
            }

            System.out.println("Total: " + playlistSimplifiedPaging.getTotal());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SpotifyWebApiException f) {
            System.out.println("Error: " + f.getMessage());
        }
    }

    public static void getListOfUsersPlaylists_Async() {
        try {
            final Future<Paging<PlaylistSimplified>> pagingFuture = getListOfUsersPlaylistsRequest.executeAsync();

            final Paging<PlaylistSimplified> playlistSimplifiedPaging = pagingFuture.get();
            for (PlaylistSimplified p: playlistSimplifiedPaging.getItems()) {
                System.out.println(p.getId());
            }

            System.out.println("Total: " + playlistSimplifiedPaging.getTotal());
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (ExecutionException f) {
            System.out.println("Error: " + f.getCause().getMessage());
        }
    }

    public static void main(String[] args) {
        Playlists program = new Playlists();
        program.getListOfUsersPlaylists_Sync();
    }
}