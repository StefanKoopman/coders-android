package move4mobile.coders.network;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by stefankoopman on 20/03/17.
 */

public interface FdyrController  {

    @GET("/article/list")
    Call<Void> getArticles();

}
