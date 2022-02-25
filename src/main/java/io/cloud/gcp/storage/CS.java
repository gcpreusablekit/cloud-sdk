package io.cloud.gcp.storage;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Storage;
public class CS {

    private static CS instance = null;

    Storage storage;
    private CS(GoogleCredentials credentials) {
    storage =  StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    }

    public static CS getInstance(GoogleCredentials credentials) {
        if (instance == null)
            instance = new CS(credentials);

        return instance;
    }

    public Bucket createBucket(String bucketName) {

        Bucket bucket = null;
        try {
            bucket = storage.create(BucketInfo.newBuilder(bucketName).build());

            System.out.println("Created bucket " + bucket.getName());
        } catch (Exception e) {
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

}
