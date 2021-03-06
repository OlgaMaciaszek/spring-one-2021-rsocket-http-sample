# SpringOne 2021 Spring Cloud RSocket Broker and RSocket Http Bridge Sample

The sample contains:

- **Verification Service** that classifies the customers as `valid` or `fraud`. This is a
  new service that uses RSocket for communication. You can use profiles to run multiple
  instances of this service.
- **RSocket Broker** that the VerificationService registers with. The broker, built on top
  of `io.rsocket.broker:rsocket-broker-spring` allows for keeping track of and
  selecting appropriate RSocket service using load-balancing and selection criteria passed
  to it.
- **Loan Service** that is a legacy service for requesting loans that uses HTTP for
  communication, but needs to communicate with the VerificationService that only speaks
  RSocket to validate its customers.
- **Gateway** is a Spring Cloud Gateway instance that also has contains
  the `io.rsocket.broker:rsocket-broker-http-bridge` dependency within, which makes it a
  RSocket Http Bridge instance. It receives HTTP requests from the Loan Service and passes
  them on via RSocket to Verification Service and then passes the responses back to the
  Loan Service.

## Test the sample

- Run `BrokerApplication`, `GatewayApplication`, `VerificationServiceApplication`
  and `LoanServiceApplication`.

- Use provided JSON files (`valid.json` and `fraud.json`) to see the customers verified
  with the HTTP and RSocket requests passing through Loan Service -> RSocket HTTP Bridge
  -> RSocket Broker -> Verification Service and back.
  
`http POST localhost:9180/verify < valid.json`
`http POST localhost:9180/verify < fraud.json`

## Test interacting directly with the bridge

You can also check interacting directly with gateway/bridge:

`http POST localhost:9081/verify < valid.json`
`http POST localhost:9081/verify < fraud.json`

## Test using tags for Verification Service instance selection

RSocket HTTP Bridge allows you to set RSocket Routing tags via headers.

Try running the request directly against the gateway/ bridge specifying an `INSTANCE_NAME` tag of an instance that is not
present:

`http POST localhost:9081/verify < valid.json 'X-RSocket-Tags:INSTANCE_NAME=verification-service-2'
`

It will return a `500`.

Run an instance that meets the tag criteria:
`cd verification-service`
`../mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=verification2"`

Rerun the HTTP request again - you should receive a correct response.

The LoanService app will set the same tag via headers if you run it with `verification2` profile.