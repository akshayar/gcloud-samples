# Spring Cloud GCP Pub/Sub Code Samples

The code samples demonstrate two ways to send messages to and receive messages from [Cloud Pub/Sub](https://cloud.google.com/pubsub/docs/) from your Spring application using: 

* [Spring Integration Channel Adapters](https://cloud.spring.io/spring-cloud-gcp/reference/html/#channel-adapters-for-cloud-pubsub)
* [Spring Cloud Stream Binders](https://cloud.spring.io/spring-cloud-gcp/reference/html/#spring-cloud-stream)

When the application starts, it will do the following every ten seconds: 
1. send a message which contains a random integer [0-1000) to a Pub/Sub topic `topic-one` via a Spring Cloud Stream output binder; 
1. the message is then received by the application via a Spring Integration inbound channel adapter configured to listen to `sub-one`; 
1. the same message is published to a second Pub/Sub topic `topic-two` via a Spring Integration outbound channel adapter;
1. the message is received again by the application via a Spring Cloud Stream input binder bound to `topic-two`.


## Build and Run
### Run as Java Application

This sample requires [Java](https://www.java.com/en/download/) and [Maven](http://maven.apache.org/) for building the application.

1.  **Follow the Java development environment set-up instructions in [the documentation](https://cloud.google.com/java/docs/setup).**

1.  Enable APIs for your project.
    [Click here](https://console.cloud.google.com/flows/enableapi?apiid=pubsub.googleapis.com&showconfirmation=true)
    to visit Cloud Platform Console and enable the Google Cloud Pub/Sub API.

1.  Create a new topic `topic-one` and attach a subscription `sub-one` to it, then do the same for `topic-two` and `sub-two`, via the Cloud Platform Console's
    [Cloud Pub/Sub section](http://console.cloud.google.com/pubsub).

1.  Enable application default credentials by running the command `gcloud auth application-default login`.

1.  Run the following Maven command to run `PubSubApplication`:
    ```
    mvn clean spring-boot:run
    ```
    You should observe an incoming message getting sent to `topic-one`, received from `sub-one`, sent to `topic-two`, and received from `topic-two` in the logged messages:
    ```
    2020-08-10 17:29:18.807  INFO 27310 --- [           main] demo.PubSubApplication                   : Started PubSubApplication in 6.063 seconds (JVM running for 6.393)
    2020-08-10 17:29:27.084  INFO 27310 --- [      elastic-3] demo.PubSubApplication                   : Sending a message via the output binder to topic-one! Payload: message-548
    2020-08-10 17:29:27.604  INFO 27310 --- [sub-subscriber1] o.s.i.h.s.MessagingMethodInvokerHelper   : Overriding default instance of MessageHandlerMethodFactory with provided one.
    2020-08-10 17:29:27.608  INFO 27310 --- [sub-subscriber1] demo.PubSubApplication                   : Message arrived via an inbound channel adapter from sub-one! Payload: message-548
    2020-08-10 17:29:28.269  INFO 27310 --- [bsub-publisher1] demo.PubSubApplication                   : Message was sent via the outbound channel adapter to topic-two!
    2020-08-10 17:29:28.269  INFO 27310 --- [sub-subscriber2] o.s.i.h.s.MessagingMethodInvokerHelper   : Overriding default instance of MessageHandlerMethodFactory with provided one.
    2020-08-10 17:29:28.290  INFO 27310 --- [sub-subscriber2] demo.PubSubApplication                   : Message arrived via an input binder from topic-two! Payload: message-548
    ```

### Run as GKE

1. gcloud pubsub topics create projects/bigqueryproject-310208/topics/topic-one

1. gcloud pubsub topics create projects/bigqueryproject-310208/topics/topic-two
 
1. gcloud pubsub subscriptions create sub-one --topic topic-one --project bigqueryproject-310208

1. mvn clean package

1. docker push arawa3/googlepubsub-spring 

1. https://cloud.google.com/kubernetes-engine/docs/tutorials/authenticating-to-cloud-platform#step_3_create_service_account_credentials

1. gcloud container clusters create first-test --zone=asia-south1-a --project=bigqueryproject-310208 --network=mumbai-vpc --subnetwork=public

1. gcloud container clusters get-credentials first-test  --zone asia-south1-a

1. kubectl create secret generic pubsub-key --from-file=key.json=/Users/rawaaksh/Downloads/bigqueryproject-310208-dc7d58fd6be8.json

1. kubectl apply -f kubernates.yaml 

1. kubectl get pods 

1. kubectl logs <podid>

1. gcloud container clusters delete first-test --zone=asia-south1-a --project=bigqueryproject-310208

1. gcloud pubsub subscriptions delete sub-one --project bigqueryproject-310208

1. gcloud pubsub topics delete projects/bigqueryproject-310208/topics/topic-one

1. gcloud pubsub topics delete projects/bigqueryproject-310208/topics/topic-two



