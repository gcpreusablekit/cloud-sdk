package io.cloud.gcp.Cloudstorage;



public class Main {
    public static void main(String[] args){

        String projectId = "qea-sandbox";
        String bucketName = "qea-reusablekit1";
        String objectName = "kitten";
        String filePath = "C:/Users/ASWYAYPFST/Downloads/images/Kitten.png" ;

        storage s=new storage();

     //  s.createBucket(projectId,bucketName);
     //  s.getBucketMetadata(projectId,bucketName);
       s.listBuckets(projectId);
        //s.uploadObject( projectId, bucketName, objectName, filePath);
      //  s.makeObjectPublic(projectId,bucketName,objectName);
        //s.listObjects(projectId,bucketName);
        //s.deleteObject(projectId,bucketName,objectName);
       // s.deleteBucket(projectId,bucketName);
    }

}
