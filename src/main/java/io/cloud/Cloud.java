package io.cloud;

import io.cloud.core.Gcp;

public class Cloud {

    public Gcp gcp(){
        return Gcp.getInstance();
    }
}
