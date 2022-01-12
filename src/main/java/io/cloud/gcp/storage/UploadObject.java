package io.cloud.gcp.storage;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class UploadObject {

    public static void main(String[] args) throws IOException {

        String projectId = "qea-sandbox";

        // The ID to give your GCS bucket
        String bucketName = "qea-reusablekit1";



        // The ID of your GCS object
        String objectName = "kitten";

        // The path to your file to upload
        String filePath = "C:/Users/ASWYAYPFST/Downloads/images/Kitten.png" ;

        uploadObject( projectId, bucketName, objectName, filePath);
    }
    public static void uploadObject(
            String projectId, String bucketName, String objectName, String filePath) throws IOException
    {


        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

        System.out.println(
                "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
    }
}
