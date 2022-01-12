package io.cloud.gcp.storage;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class DeleteObject {
    public static void main(String[] args)
    {

        String projectId = "qea-sandbox";

        // The ID to give your GCS bucket
        String bucketName = "qea-reusablekit1";
        // The ID of your GCS object
        String objectName = "kitten";

        deleteObject(projectId,bucketName,objectName);
    }
    public static void deleteObject(String projectId, String bucketName, String objectName) {


        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        storage.delete(bucketName, objectName);

        System.out.println("Object " + objectName + " was deleted from " + bucketName);
    }
}
