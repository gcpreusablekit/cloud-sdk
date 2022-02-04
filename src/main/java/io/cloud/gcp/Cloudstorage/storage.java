package io.cloud.gcp.Cloudstorage;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class storage {
    public static void main(String[] args) {

        String projectId = "qea-sandbox";
        String bucketName = "qea-reusablekit1";
        String objectName = "kitten";
        String filePath = "C:/Users/ASWYAYPFST/Downloads/images/Kitten.png" ;
        storage s=new storage();
        s.createBucket(projectId,bucketName);
       s.getBucketMetadata(projectId,bucketName);
        s.listBuckets(projectId);
        s.uploadObject( projectId, bucketName, objectName, filePath);
        s.makeObjectPublic(projectId,bucketName,objectName);
        s.listObjects(projectId,bucketName);
       s.deleteObject(projectId,bucketName,objectName);
       s.deleteBucket(projectId,bucketName);
    }
    public BucketInfo createBucket(String projectId, String bucketName) {

        Bucket bucket = null;
        try {
            Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

            bucket = storage.create(BucketInfo.newBuilder(bucketName).build());

            System.out.println("Created bucket " + bucket.getName());
        } catch (Exception e) {
            System.err.println("Bucket not created");
        }
        return bucket;
    }
    public Page<Bucket> listBuckets(String projectId) {
        Page<Bucket> buckets = null;
        try {
            Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
            buckets = storage.list();

            for (Bucket bucket : buckets.iterateAll()) {
                System.out.println(bucket.getName());
            }
        } catch (Exception e) {
            System.out.println("NO BUCKETS");
        }
        return buckets;
    }
    public BlobId uploadObject(
            String projectId, String bucketName, String objectName, String filePath) {

        BlobId blobId = null;
        try {
            Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
            blobId = BlobId.of(bucketName, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

            System.out.println(
                    "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
        } catch (Exception e) {
            System.out.print("Object not uploaded into the bucket");
        }
        return blobId;
    }

    public BlobId makeObjectPublic(String projectId, String bucketName, String objectName) {

        try {
            Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
            BlobId blobId = BlobId.of(bucketName, objectName);
            storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

            System.out.println(
                    "Object " + objectName + " in bucket " + bucketName + " was made publicly readable");
        } catch (Exception e) {
            System.out.println("Error in setting object as public");
        }
        return null;
    }

    public Page<Blob> listObjects(String projectId, String bucketName) {

        Page<Blob> blobs = null;
        try {
            Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
            blobs = storage.list(bucketName);

            for (Blob blob : blobs.iterateAll()) {
                System.out.println(blob.getName());
            }
        } catch (Exception e) {
            System.out.println("No objects Founded");
        }
        return blobs;
    }

    public StorageOptions getBucketMetadata(String projectId, String bucketName) {

        try {
            Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

            // Select all fields. Fields can be selected individually e.g. Storage.BucketField.NAME
            Bucket bucket =
                    storage.get(bucketName, Storage.BucketGetOption.fields(Storage.BucketField.values()));

            // Print bucket metadata
            System.out.println("BucketName: " + bucket.getName());
            System.out.println("DefaultEventBasedHold: " + bucket.getDefaultEventBasedHold());
            System.out.println("DefaultKmsKeyName: " + bucket.getDefaultKmsKeyName());
            System.out.println("Id: " + bucket.getGeneratedId());
            System.out.println("IndexPage: " + bucket.getIndexPage());
            System.out.println("Location: " + bucket.getLocation());
            System.out.println("LocationType: " + bucket.getLocationType());
            System.out.println("Metageneration: " + bucket.getMetageneration());
            System.out.println("NotFoundPage: " + bucket.getNotFoundPage());
            System.out.println("RetentionEffectiveTime: " + bucket.getRetentionEffectiveTime());
            System.out.println("R" +
                    "etentionPeriod: " + bucket.getRetentionPeriod());
            System.out.println("RetentionPolicyIsLocked: " + bucket.retentionPolicyIsLocked());
            System.out.println("RequesterPays: " + bucket.requesterPays());
            System.out.println("SelfLink: " + bucket.getSelfLink());
            System.out.println("StorageClass: " + bucket.getStorageClass().name());
            System.out.println("TimeCreated: " + bucket.getCreateTime());
            System.out.println("VersioningEnabled: " + bucket.versioningEnabled());

        }catch(Exception e){
            System.out.println("Bucket not founded");
        }
        return null;
    }
        public StorageOptions deleteObject (String projectId, String bucketName, String objectName){
            try {

                Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
                storage.delete(bucketName, objectName);

                System.out.println("Object " + objectName + " was deleted from " + bucketName);
            } catch (Exception e) {
                System.out.println("Object not deleted");
            }
            return null;
        }


        public StorageOptions deleteBucket (String projectId, String bucketName){
            try {

                Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
                Bucket bucket = storage.get(bucketName);
                bucket.delete();

                System.out.println("Bucket " + bucket.getName() + " was deleted");

            } catch (Exception e) {
                System.err.println(" Bucket not deleted");
            }
            return null;
        }
    }

