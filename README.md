# RH-SSO SHA-256 support

SHA-256 hashing support for legacy compatibility.

DEPRECATED! DO NOT USE !

## Versioning

Refer to [Semantic Versioning 2.0.0](http://semver.org/).

## Deployment on Maven repository

 ```bash
 mvn clean deploy -Pnexus
 ```
**Nexus** maven profile defines:

    <nexus.url.release>${nexus.url}/content/repositories/releases</nexus.url.release>
    <nexus.url.snapshot>${nexus.url}/content/repositories/snapshots</nexus.url.snapshot>


### Deployment

Create a JBoss module:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.1" name="com.lyra.idm.keycloak">
  <resources>
    <resource-root path="sha256-0.0.1.jar" />
  </resources>
  <dependencies>
    <module name="javax.activation.api" />
    <module name="org.jboss.logging" />
    <module name="org.keycloak.keycloak-common" />
    <module name="org.keycloak.keycloak-core" />
    <module name="org.keycloak.keycloak-server-spi" />
    <module name="org.keycloak.keycloak-server-spi-private" />
    <module name="org.keycloak.keycloak-services" />
  </dependencies>
</module>
```

## License

* [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Release Notes

### 0.0.1

* SHA-256 implementation

## Author

Sylvain M. for [Lyra](https://lyra.com).

Inspired from [leroyguillaume/keycloak-bcrypt](https://github.com/leroyguillaume/keycloak-bcrypt)

