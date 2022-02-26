package com.bukazoid.gateway.client.test

import com.bukazoid.gateway.GatewayClient
import com.bukazoid.gateway.client.test.handlers.ClientHandler
import com.bukazoid.gateway.common.settings.GatewaySettings

fun main() {
    val gwClient = GatewayClient("localhost", GatewaySettings.PORT_CLIENT)
    gwClient.startEpoll(ClientHandler())
}
