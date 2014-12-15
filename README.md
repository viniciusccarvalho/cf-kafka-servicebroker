cf-kafka-servicebroker
======================

Basic kafka service broker for Cloud Foundry. This broker connects to an external zookeeper, the application will look for a system property called zookeeper.url bound to it.

This broker is deployed by [Kafka BOSH release](https://github.com/viniciusccarvalho/kafka-boshrelease) and the job that deploys it binds the proper zookeeper url.

How does it work?
=================

This broker could not be simpler. Kafka does not have a notion of multi tenancy or even authentication. Clients that create instances or bind apps to it, only receive a json credentials object with the zookeeper url (for consumers) and a list of brokers (for producers). The broker stores the subscritptions inside zookeeper under the /sb/ path.
