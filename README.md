# ABE-server

ABE stands for Attribute Based Encryption. It is a way to manage cyphertext encryption with a combination of keys in order to allow the usage of 
Access Structures (AS) for encryption. An **AS** is a plein text logical expression of the attributes required to decrypt the cyphertext. For example

```
(Attribute1 AND Attribute2) OR Attribute2
```

Attributes are merely descriptions of keys and can be arbitrarily named. But most often, they express roles or permission levels like _User_, _Admin_, _Customer_, etc.

## The application

The ABE-Server application is a demo app that allows for easy interaction with the [DCP-ABE library](https://github.com/managination/dcpabe). It provides
endpoints for Create Read Update Delete of:

* Authority and attributes
* Client
* Client access to keys
* Message encryption and decryption

It is a **_DEMO_** application and provides no security and limits of any kind. Do not expect to be asked for authentication or be restricted of doing stuff that breaks the system

## Tests and documentation

We have created an executable documentation in the form of Gherkin tests executed with cucumber. These tests can be executed by running `mvn cuke` in the terminal. 
