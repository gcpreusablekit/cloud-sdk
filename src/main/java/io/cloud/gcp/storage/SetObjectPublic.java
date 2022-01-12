package io.cloud.gcp.storage;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class SetObjectPublic {
    public static void main(String[] args)
    {
        String projectId = "qea-sandbox";

        // The ID to give your GCS bucket
        String bucketName = "qea-reusablekit1";
        // The ID of your GCS object
        String objectName = "kitten";

        makeObjectPublic(projectId,bucketName,objectName);
    }
    public static void makeObjectPublic(String projectId, String bucketName, String objectName) {

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        System.out.println(
                "Object " + objectName + " in bucket " + bucketName + " was made publicly readable");
    }
}
