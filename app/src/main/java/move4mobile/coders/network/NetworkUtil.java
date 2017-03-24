package move4mobile.coders.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by stefankoopman on 19/03/17.
 */

public class NetworkUtil {

    private static Retrofit retrofit;

    public static GithubController getGithubController(){
        if(retrofit == null){
            createRetro();
        }
        return retrofit.create(GithubController.class);
    }

    public static RepoController getRetroController(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/users/").addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(RepoController.class);
    }

    public static RepoController getUserController(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/user/").addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(RepoController.class);
    }

    public static FdyrController getFdyr(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.fdyr.nl/").addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(FdyrController.class);
    }


    private static void createRetro(){
        retrofit = new Retrofit.Builder().baseUrl("https://github.com/login/oauth/").addConverterFactory(GsonConverterFactory.create()).build();
    }

}
