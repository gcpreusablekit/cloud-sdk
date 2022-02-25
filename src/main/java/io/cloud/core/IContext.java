package io.cloud.core;




import io.cloud.gcp.gke.Gke;
import io.cloud.gcp.storage.CS;

import java.io.IOException;

public interface IContext {


   Gke getGke() throws IOException;
   CS getStorage() throws IOException;

}
