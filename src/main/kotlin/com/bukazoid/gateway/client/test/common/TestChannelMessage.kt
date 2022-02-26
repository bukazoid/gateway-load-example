package com.bukazoid.gateway.client.test.common

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(TestHelloMessage::class),// need a test to check if types are simple class names
    JsonSubTypes.Type(TestLoadMessage::class)
)
abstract class TestChannelMessage() {

}