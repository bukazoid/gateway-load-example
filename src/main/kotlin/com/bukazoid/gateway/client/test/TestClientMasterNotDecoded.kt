package com.bukazoid.gateway.client.test

import com.bukazoid.gateway.GatewayMaster
import com.bukazoid.gateway.client.test.handlers.MasterHandlerNotDecoded
import com.bukazoid.gateway.common.settings.GatewaySettings

fun main() {
    val gwClient = GatewayMaster("localhost", GatewaySettings.PORT_SERVER)
    gwClient.startEpoll(MasterHandlerNotDecoded())
}
