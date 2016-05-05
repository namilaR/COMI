package Appclasses;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import Appclasses.ServerConManager;
/**
 * Created by dealwis on 4/1/16.
 */
public class Intrest {


    private String image = "";
    private ServerConManager SerConObject = new ServerConManager();
    private HttpURLConnection ser_conn = null;


    public String upload_user_shared_intrest(String image,String name,String caption){



        this.ser_conn = this.SerConObject.get_server_connection_UP();

        try {

            String urlParameters ;
            String Server_API_method = "upload_shared_intrest";

            urlParameters = "image=" + URLEncoder.encode(image, "UTF-8")
                    + "&method=" + URLEncoder.encode(Server_API_method, "UTF-8")
                    + "&name=" + URLEncoder.encode(name, "UTF-8")
                    + "&caption=" + URLEncoder.encode(caption, "UTF-8");

            String Ser_Response = this.SerConObject.send_POST_HTTP_request(ser_conn, urlParameters);


            JSONArray jsonArray = new JSONArray((Ser_Response));

            JSONObject jsonobject = jsonArray.getJSONObject(0);
            String Response = jsonobject.getString("response");



            return Response;



        }catch(Exception e) {

            return "error in upload_user_shared_intrest";
        }



    }
    public String sendGetRequest(String uri) {
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String result;

            StringBuilder sb = new StringBuilder();

            while((result = bufferedReader.readLine())!=null){
                sb.append(result);
            }

            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public String sendPostRequest(String requestURL,
                                  HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                response = br.readLine();
            } else {
                response = "Error Registering";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }




}
