package move4mobile.coders.models;

/**
 * Created by stefankoopman on 19/03/17.
 */

public class TokenRequestObject {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_ur;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_ur() {
        return redirect_ur;
    }

    public void setRedirect_ur(String redirect_ur) {
        this.redirect_ur = redirect_ur;
    }
}
