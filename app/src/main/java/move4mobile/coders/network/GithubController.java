package move4mobile.coders.network;

import move4mobile.coders.models.AccessTokenObject;
import move4mobile.coders.models.TokenRequestObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by stefankoopman on 19/03/17.
 */

public interface GithubController {

    @GET("authorize")
    Call<Void> getCode(@Query("client_id") String clientId);

    @POST("access_token")
    @Headers("Accept: application/json")
    Call<AccessTokenObject> getAccesstoken(@Body TokenRequestObject tokenRequestObject);

}
