package io.cloud.gcp.storage;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class ListObject {
    public static void main(String[] args)
    {

        String projectId = "qea-sandbox";

        // The ID to give your GCS bucket
        String bucketName = "qea-reusablekit";
        listObjects(projectId,bucketName);
    }
    public static void listObjects(String projectId, String bucketName) {

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Page<Blob> blobs = storage.list(bucketName);

        for (Blob blob : blobs.iterateAll()) {
            System.out.println(blob.getName());
        }
    }
}
