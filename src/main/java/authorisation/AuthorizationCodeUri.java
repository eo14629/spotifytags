package authorisation;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AuthorizationCodeUri {
    private static final String clientId = "273a00cb419444ebb3e5d08e73a98451";
    private static final String clientSecret = "b910b1ae852649268b8237ab37889c6c";
    private static String uriString = "";
    private static String scope = "";
    private static URI uri;

    AuthorizationCodeUri(){}
    AuthorizationCodeUri(String scope, String uriString) {
        this.scope = scope;
        this.uriString = uriString;
    }


    public static void authorizationCodeUri_Sync() {
        System.out.println("scope: " + scope + "\nuriString: " + uriString);
        URI redirectUri = SpotifyHttpManager.makeUri(uriString);
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope(scope)
                .show_dialog(true)
                .build();
        uri = authorizationCodeUriRequest.execute();

        System.out.println("URI: " + uri.toString());
    }

    public URI getUri() {
        URI uri_copy = this.uri;
        return uri_copy;
    }

    public static void main(String[] args) {
        AuthorizationCodeUri program = new AuthorizationCodeUri("playlist-read-private", "http://localhost:8888/callback");
        program.authorizationCodeUri_Sync();
    }
}