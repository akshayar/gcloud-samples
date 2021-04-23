gcloud functions deploy googlepubsub-cloud-function \
--entry-point com.example.Example \
--runtime java11 --project bigqueryproject-310208 \
--memory 512MB --trigger-topic first-test --allow-unauthenticated  

gcloud pubsub topics publish projects/bigqueryproject-310208/topics/first-test  --message="Hello I am testing 3"