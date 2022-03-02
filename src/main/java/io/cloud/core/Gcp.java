package io.cloud.core;

import io.cloud.gcp.gke.Gke;
import io.cloud.gcp.storage.CS;
import java.io.IOException;

public class Gcp implements IContext {

    private static Gcp instance = null;

    public static Gcp getInstance() {
        if (instance == null)
            instance = new Gcp();

        return instance;
    }

   private static  String projectID;

    public static void setProjectId(String s) {


    }

    @Override
    public Gke getGke() throws IOException {

        return Gke.getInstance(projectID);
    }

    @Override
    public CS getStorage()  throws IOException {
        return  CS.getInstance(projectID);
    }
}
