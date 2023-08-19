# ms-board-bff

# Read Me First
The following was discovered as part of building this project:


# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.4/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

## Checklist Microservice Chassis

| Funcionalidade                                                                                                                                             | [Requirement Level](https://datatracker.ietf.org/doc/html/rfc2119) | Status                     | Observações                                                                                                           |
| ---------------------------------------------------------------------------------------------------------------------------------------------------------- |--------------------------------------------------------------------|----------------------------| --------------------------------------------------------------------------------------------------------------------- |
| [Autenticação](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#autentica%C3%A7%C3%A3o)                                 | jwt                                                                | jwt                        |                                                                                                                       |
| [Autorização](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#autorização)                                             | MUST                                                               | :eight_pointed_black_star: |                                                                                                                       |
| [Circuit Breaker](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#circuit-breaker)                                     | MAY                                                                | :eight_pointed_black_star: |                                                                                                                       |
| [Comunicação via HTTP](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#comunicação-via-http)                           | SHOULD                                                             | :white_check_mark:         | Seguir o [Padrão Rest](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-024%3A%20Padr%C3%A3o%20de%20APIs%20Rest.md)   |
| [Retries/Timeouts/Backoffs](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#retriestimeoutsbackoffs)                   | MAY                                                                |                            |                                                                                                                       |
| [Comunicação via gRPC](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#comunicação-via-grpc)                           | MAY                                                                |                            |                                                                                                                       |
| [Idempotência](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#idempotencia)                                           | SHOULD                                                             |                            |                                                                                                                       |
| [Log centralizado](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#log-centralizado)                                   | MUST                                                               | :white_check_mark:         |                                                                                                                       |
| [Métricas](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#métricas)                                                   | MUST                                                               | :eight_pointed_black_star: |                                                                                                                       |
| [Distributed tracing](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#distributed-tracing)                             | MUST                                                               | :eight_pointed_black_star: |                                                                                                                       |
| [Rate Limit](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#rate-limit)                                               | SHOULD                                                             | :eight_pointed_black_star: |                                                                                                                       |
| [Graceful Shutdown](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#graceful-shutdown)                                 | MUST                                                               | :white_check_mark:         |                                                                                                                       |
| [Service Discovery](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#service-discovery)                                 | MAY                                                                |                            |                                                                                                                       |
| [Comunicação via Kafka](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#comunicação-via-kafka)                         | MAY                                                                |                            |                                                                                                                       |
| [Documentação](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#documentação)                                           | MUST                                                               | :white_check_mark:         | Documentar a API usando [Swagger](https://picpay.atlassian.net/wiki/spaces/MOON/pages/2589098504/Configurar+API+Docs) |
| [Deploy automatizado](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#deploy-automatizado)                             | MUST                                                               | :white_check_mark:         |                                                                                                                       |
| [Ambiente local de desenvolvimento](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#ambiente-local-de-desenvolvimento) | SHOULD                                                             | :eight_pointed_black_star: |                                                                                                                       |
| [Testes](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#testes)                                                       | MUST                                                               | :eight_pointed_black_star: |                                                                                                                       |
| [Conexão com banco de dados](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#conexão-com-banco-de-dados)               | MAY                                                                |                            |                                                                                                                       |
| [Variáveis de ambiente](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#variáveis-de-ambiente)                         | MUST                                                               | :eight_pointed_black_star: |                                                                                                                       |
| [Secrets](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#secrets)                                                     | MAY                                                                | :eight_pointed_black_star: |                                                                                                                       |
| [Health check](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#health-check)                                           | MUST                                                               | :white_check_mark:         |                                                                                                                       |
| [Feature Flags](https://github.com/PicPay/RFC/blob/main/RFCs/RFC-014%3A%20Microservice%20Chassis.md#feature-flags)                                         | MAY                                                                |                            |                                                                                                                       |

### Run in dev mode
#### requirements local
* java 17
*  maven  3.8.3 ou superior