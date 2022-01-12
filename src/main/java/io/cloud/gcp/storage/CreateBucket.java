package io.cloud.gcp.storage;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

//import java.io.IOException;
//import java.util.concurrent.ExecutionException;

public class CreateBucket {
    public static void main(String[] args)
             {

        String projectId = "qea-sandbox";

        // The ID to give your GCS bucket
        String bucketName = "qea-reusablekit1";
        createBucket(projectId,bucketName);
    }

    public static void createBucket(String projectId, String bucketName) {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID to give your GCS bucket
        // String bucketName = "your-unique-bucket-name";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        Bucket bucket = storage.create(BucketInfo.newBuilder(bucketName).build());

        System.out.println("Created bucket " + bucket.getName());
    }
}
