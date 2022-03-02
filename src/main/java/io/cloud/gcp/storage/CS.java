package io.cloud.gcp.storage;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CS {

    private static CS instance = null;

    Storage storage;

    public CS(String projectId) {
        storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

    }

    public static CS getInstance(String credentials) {
        if (instance == null)
            instance = new CS(credentials);

        return instance;
    }

    public Bucket createBucket(String bucketName) {
        Bucket bucket = null;
       try {
    bucket = storage.create(BucketInfo.newBuilder(bucketName).build());
    System.out.println("Created bucket " + bucket.getName());
     }catch (Exception e) {
    System.err.println("Bucket not created");
      }
        return bucket;
    }

    public Page<Bucket> listBuckets() {
        Page<Bucket> buckets = null;
        try {
            buckets = storage.list();
            for (Bucket bucket : buckets.iterateAll()) {
                System.out.println(bucket.getName());
            }
        } catch (Exception e) {
            System.out.println("NO BUCKETS");
        }
        return buckets;
    }

    public BlobId uploadObject(String bucketName, String objectName, String filePath) {
        BlobId blobId = null;
        try {
       blobId = BlobId.of(bucketName, objectName);
       BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
       storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));
       System.out.println(
            "Object"+objectName+"uploaded successfully");
    }catch (Exception e) {
        System.out.print("Object not uploaded into the bucket");
    }
        return blobId;
    }
    public BlobId makeObjectPublic( String bucketName, String objectName) {
        try {
            BlobId blobId = BlobId.of(bucketName, objectName);
            storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
            System.out.println(
                    "Object " + objectName + " in bucket " + bucketName + " was made publicly readable");
        } catch (Exception e) {
            System.err.println("Error in setting object as public");
        }
        return null;
    }
    public BlobId deleteObject (String bucketName, String objectName){
        try {
            storage.delete(bucketName,objectName);
            System.out.println("Object " + objectName + " was deleted from " + bucketName);
        } catch (Exception e) {
            System.out.println("Object not deleted");
        }
        return null;
    }
    public Bucket deleteBucket(String bucketName) {
        try{
        Bucket bucket = storage.get(bucketName);
        bucket.delete();
        System.out.println("Bucket " + bucket.getName() + " was deleted");
       }catch (Exception e) {
            System.err.println(" Bucket not deleted");
        }
        return null;
    }
}