package Appclasses;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.util.Arrays;

import Appclasses.ServerConManager;
/**
 * Created by dealwis on 3/27/16.
 */
public class User {

    private String username = "";
    private String password = "";
    private String fname = "";
    private String lname = "";
    private String dob = "";
    private String contact = "";
    private String email = "";
    private ServerConManager SerConObject = new ServerConManager();
    private HttpURLConnection ser_conn = null;


    public String create_new_user_account(String UserName,String FName,String LName,String Email,String DOB,String Password,String Contact){


        this.ser_conn = this.SerConObject.get_server_connection_UP();

        try {

                String urlParameters ;
                String Server_API_method = "create_user_account";

                    urlParameters = "username=" + URLEncoder.encode(UserName, "UTF-8") + "&password=" + URLEncoder.encode(Password, "UTF-8")
                                    + "&fname=" + URLEncoder.encode(FName, "UTF-8") + "&lname=" + URLEncoder.encode(LName, "UTF-8")
                                    + "&$dob=" + URLEncoder.encode(DOB, "UTF-8") + "&contact=" + URLEncoder.encode(Contact, "UTF-8")
                                    + "&email=" + URLEncoder.encode(Email, "UTF-8")
                                    + "&method=" + URLEncoder.encode(Server_API_method, "UTF-8");

                    String Ser_Response = this.SerConObject.send_POST_HTTP_request(ser_conn, urlParameters);


            JSONArray jsonArray = new JSONArray((Ser_Response));

            JSONObject jsonobject = jsonArray.getJSONObject(0);
            String Response = jsonobject.getString("response");



            return Response;



        }catch(Exception e) {

           return "error in create_new_user";
        }

    }

    public String user_login_request(String Email,String Password){
        this.ser_conn = this.SerConObject.get_server_connection_UP();

        try {

            String urlParameters ;
            String Server_API_method = "user_login_check";

            urlParameters = "email=" + URLEncoder.encode(Email, "UTF-8") + "&password=" + URLEncoder.encode(Password, "UTF-8")
                    + "&method=" + URLEncoder.encode(Server_API_method, "UTF-8");

            String Ser_Response = this.SerConObject.send_POST_HTTP_request(ser_conn, urlParameters);


            JSONArray jsonArray = new JSONArray((Ser_Response));
            JSONObject jsonobject = jsonArray.getJSONObject(0);
            String Response = jsonobject.getString("response");

            return Response;

        }catch(Exception e) {

            return "error in login";
        }
    }

}
