package io.cloud.gcp.ComputeEngine;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.compute.v1.*;

import java.util.UUID;
public class compute {
    public static void main(String[] args) {
        String project = "qea-sandbox";
        String zone = "us-central1-a";
        String instanceName = "instance-name4";
        String firewallRuleName = "firewall-rule-name-1" + UUID.randomUUID();
        String network = "global/networks/default";

        compute c = new compute();
         c.createInstance(project, zone, instanceName);
         c.createFirewall(project, firewallRuleName, network);
         c.deleteFirewallRule(project, firewallRuleName);
         c.resetInstance(project, zone, instanceName);
        c.deleteInstance(project, zone, instanceName);
    }

    public Operation createInstance(String project, String zone, String instanceName) {

        String machineType = String.format("zones/%s/machineTypes/n1-standard-1", zone);
        String sourceImage = String
                .format("projects/debian-cloud/global/images/family/%s", "debian-10");
        long diskSizeGb = 10L;
        String networkName = "default";


        Operation response = null;
        try (InstancesClient instancesClient = InstancesClient.create()) {
            // Instance creation requires at least one persistent disk and one network interface.
            AttachedDisk disk =
                    AttachedDisk.newBuilder()
                            .setBoot(true)
                            .setAutoDelete(true)
                            .setType(AttachedDisk.Type.PERSISTENT.toString())
                            .setDeviceName("disk-1")
                            .setInitializeParams(
                                    AttachedDiskInitializeParams.newBuilder().setSourceImage(sourceImage)
                                            .setDiskSizeGb(diskSizeGb).build())
                            .build();

            // Use the network interface provided in the networkName argument.
            NetworkInterface networkInterface = NetworkInterface.newBuilder().setName(networkName)
                    .build();

            Instance instanceResource =
                    Instance.newBuilder()
                            .setName(instanceName)
                            .setMachineType(machineType)
                            .addDisks(disk)
                            .addNetworkInterfaces(networkInterface)
                            .build();

            System.out.printf("Creating instance: %s at %s %n", instanceName, zone);

            // Insert the instance in the specified project and zone.
            InsertInstanceRequest insertInstanceRequest = InsertInstanceRequest.newBuilder()
                    .setProject(project)
                    .setZone(zone)
                    .setInstanceResource(instanceResource).build();

            OperationFuture<Operation, Operation> operation = instancesClient.insertAsync(
                    insertInstanceRequest);

            // Wait for the operation to complete.
            response = operation.get();

            System.out.println("Operation Status: " + response.getStatus());
        } catch (Exception e) {
            System.out.println("Instance not created");
        }
        return response;
    }

    public InsertFirewallRequest createFirewall(String project, String firewallRuleName, String network) {

        try (FirewallsClient firewallsClient = FirewallsClient.create()) {

            // The below firewall rule is created in the default network.
            Firewall firewallRule = Firewall.newBuilder()
                    .setName(firewallRuleName)
                    .setDirection(Firewall.Direction.INGRESS.toString())
                    .addAllowed(
                            Allowed.newBuilder().addPorts("20").addPorts("50-60").setIPProtocol("tcp").build())
                    .addSourceRanges("0.0.0.0/0")
                    .setNetwork(network)
                    .addTargetTags("web")
                    .setDescription("Allowing TCP traffic on port 20 and 50-60 from Internet.")
                    .build();
            InsertFirewallRequest insertFirewallRequest = InsertFirewallRequest.newBuilder()
                    .setFirewallResource(firewallRule)
                    .setProject(project).build();

            firewallsClient.insertAsync(insertFirewallRequest).get();

            System.out.println("Firewall rule created successfully -> " + firewallRuleName);
        } catch (Exception e) {
            System.out.println("Firewall rule not created -> " + firewallRuleName);
        }
        return null;
    }

    public Operation deleteFirewallRule(String project, String firewallRuleName) {
        try (FirewallsClient firewallsClient = FirewallsClient.create()) {

            OperationFuture<Operation, Operation> operation = firewallsClient.deleteAsync(project,
                    firewallRuleName);
            operation.get();

            System.out.println("Deleted firewall rule -> " + firewallRuleName);
        } catch (Exception e) {
            System.out.println(" firewall rule not Deleted-> " + firewallRuleName);
        }
        return null;
    }
    public Operation resetInstance(String project, String zone, String instanceName) {

        Operation response = null;
        try (InstancesClient instancesClient = InstancesClient.create()) {

            ResetInstanceRequest resetInstanceRequest = ResetInstanceRequest.newBuilder()
                    .setProject(project)
                    .setZone(zone)
                    .setInstance(instanceName)
                    .build();

            OperationFuture<Operation, Operation> operation = instancesClient.resetAsync(
                    resetInstanceRequest);
            response = operation.get();

            System.out.println("Instance reset successfully ! " + response.getStatus());
        } catch (Exception e) {
            System.out.println("Instance not reset ");
        }
        return response;
    }
    public DeleteInstanceRequest deleteInstance(String project, String zone, String instanceName) {
        DeleteInstanceRequest respone=null;
        try (InstancesClient instancesClient = InstancesClient.create()) {

            DeleteInstanceRequest deleteInstanceRequest = DeleteInstanceRequest.newBuilder()
                    .setProject(project)
                    .setZone(zone)
                    .setInstance(instanceName).build();

            OperationFuture<Operation, Operation> operation = instancesClient.deleteAsync(
                    deleteInstanceRequest);
            Operation response = operation.get();

            System.out.println("Deleting instance" + response.getStatus());
        } catch (Exception e) {
            System.out.println("Instance deletion failed ! ! ");
        }

        return respone;
    }
}