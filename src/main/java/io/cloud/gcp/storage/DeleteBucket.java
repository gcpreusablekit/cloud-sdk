package io.cloud.gcp.storage;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class DeleteBucket {
    public static void main(String[] args)
    {

        String projectId = "qea-sandbox";

        // The ID to give your GCS bucket
        String bucketName = "qea-reusablekit1";
        deleteBucket(projectId,bucketName);
    }
    public static void deleteBucket(String projectId, String bucketName) {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of the bucket to delete
        // String bucketName = "your-unique-bucket-name";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Bucket bucket = storage.get(bucketName);
        bucket.delete();

        System.out.println("Bucket " + bucket.getName() + " was deleted");
    }
}
