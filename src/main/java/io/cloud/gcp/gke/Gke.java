package io.cloud.gcp.gke;

import com.google.cloud.container.v1.ClusterManagerClient;
import com.google.cloud.container.v1.ClusterManagerSettings;
import com.google.container.v1.Cluster;
import com.google.container.v1.CreateClusterRequest;
import com.google.container.v1.NodePool;
import com.google.container.v1.Operation;

import java.io.IOException;

public class Gke {
    private static Gke instance = null;

    ClusterManagerClient client;

    public Gke(String projectId) throws IOException {
        ClusterManagerSettings clusterManagerSettings =
                ClusterManagerSettings.newBuilder()
                        .setQuotaProjectId(projectId)
                        .build();
        client = ClusterManagerClient.create(clusterManagerSettings);

    }
    public static Gke getInstance(String credentials) throws IOException {
        if (instance == null)
            instance = new Gke(credentials);

        return instance;
    }

    public CreateClusterRequest createcluster( String ZONE, String CLUSTER_NAME) {
        try {
            NodePool nodePool =
                    NodePool.newBuilder()
                            .setInitialNodeCount(1)
                            .setName("test-node-pool")
                            .build();
            Cluster cluster =
                    Cluster.newBuilder()
                            .setName(CLUSTER_NAME)
                            .setLocation("us-central1")
                            .addNodePools(nodePool)
                            .setNetwork("default")
                            .build();
            Operation response = client.createCluster( ZONE, cluster);

            System.out.println("new cluster created" + response.getStatus());
        } catch (Exception e) {
            System.out.println("cluster not created");
        }
        return null;
    }
}