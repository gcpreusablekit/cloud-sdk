package io.cloud.gcp.gke;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.container.v1.ClusterManagerClient;
import com.google.cloud.container.v1.ClusterManagerSettings;
import com.google.container.v1.Cluster;
import com.google.container.v1.NodePool;
import com.google.container.v1.Operation;

import java.io.IOException;

public class Gke {
    private static Gke instance = null;

    ClusterManagerClient client ;

    private Gke(GoogleCredentials credentials) throws IOException {
        ClusterManagerSettings clusterManagerSettings =
    ClusterManagerSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
            .build();
        client= ClusterManagerClient.create(clusterManagerSettings);

    }

    public static Gke getInstance(GoogleCredentials credentials) throws IOException {
        if (instance == null)
            instance = new Gke(credentials);

        return instance;
    }
    public Operation createcluster(String ZONE, String CLUSTER_NAME, String NODE_POOL_NAME){

        try {
            NodePool nodePool =
                    NodePool.newBuilder()
                            .setInitialNodeCount(1)
                            .setName(NODE_POOL_NAME)
                            .build();
            Cluster cluster =
                    Cluster.newBuilder()
                            .setName(CLUSTER_NAME)
                            .setLocation("us-central1")
                            .addNodePools(nodePool)
                            .setNetwork("default")
                            .build();
          return client.createCluster( ZONE, cluster);
        } catch (Exception e) {
            System.out.println("cluster not created");
        }
        return null;
    }
}
