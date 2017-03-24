package move4mobile.coders.network;

import java.util.List;

import move4mobile.coders.models.GithubAPIUser;
import move4mobile.coders.models.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by stefankoopman on 20/03/17.
 */

public interface RepoController {

    @GET("")
    Call<List<Repo>> getRepos(@Url String url);

    @GET("")
    Call<GithubAPIUser> getUser(@Url String url);
}
