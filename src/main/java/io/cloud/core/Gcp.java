package io.cloud.core;


import java.util.Date;
import java.util.Date;
import org.joda.time.DateTime;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;


import com.google.cloud.storage.Storage;
import io.cloud.gcp.gke.Gke;

import com.google.common.collect.Lists;
import io.cloud.gcp.storage.CS;


import java.io.FileInputStream;
import java.io.IOException;

public class Gcp implements IContext {

    private static Gcp instance = null;
  private Gcp(){

  }
    public static Gcp getInstance() {
        if (instance == null)
            instance = new Gcp();

        return instance;
    }


    private static GoogleCredentials credentials;

   /* public static void setCredentials(String jsonPath) throws IOException {

        credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
    }
*/
   public static void setCredentials(String accessToken) throws IOException {
      Date expirationTime = DateTime.now().plusSeconds(60).toDate();

       credentials = GoogleCredentials.create(new AccessToken(accessToken, expirationTime));
   }

    @Override
    public Gke getGke() throws IOException {
        return Gke.getInstance(credentials);
    }

    @Override
    public CS getStorage()  throws IOException {
        return  CS.getInstance(credentials);
    }
}
