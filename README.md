# Spring Cloud Broker RSocket Http Bridge Sample

## Test RSocket Http Bridge Sample

- Run `BrokerApplication`, `BridgeApplication` and `ServiceApplication`.

Use the Bridge to communicate with the RSocket-based `ServiceApplication`, through RSocket
Broker, using an HTTP client.

Try the following commands to test the 4 RSocket Interaction modes (`rr`
- `request - response`, `rs` - `request - stream`, `rc` - `request - channel`, `ff`
- `fire and forget`):

- `http POST localhost:9080/rr/service/service-rr body=test`
- `http POST localhost:9080/rs/service/service-rs body=test`
- `http POST localhost:9080/rc/service/service-rc body=test`
- `http POST localhost:9080/ff/service/service-ff body=test`

Use the following command to test the default function resolution (`request-response`
unless otherwise specified in `spring.cloud.function.definition`):

- `http POST localhost:9080/service/service-ff body=test`