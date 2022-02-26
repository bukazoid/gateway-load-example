package com.bukazoid.gateway.client.test

import com.bukazoid.gateway.Gateway
import com.bukazoid.gateway.GatewayClient
import com.bukazoid.gateway.GatewayMaster
import com.bukazoid.gateway.client.test.handlers.ClientHandler
import com.bukazoid.gateway.client.test.handlers.MasterHandler
import com.bukazoid.gateway.common.settings.GatewaySettings

fun main() {
    // start gateway
    Thread {
        val gwServer = Gateway(GatewaySettings.PORT_CLIENT)
        gwServer.startEpoll()
    }.start()

    // start channel master
    Thread {
        Thread.sleep(200)// just in case
        val gwClient = GatewayMaster("localhost", GatewaySettings.PORT_SERVER)
        gwClient.startEpoll(MasterHandler())
    }.start()

    // start channel client
    Thread {
        Thread.sleep(1000)// just in case
        val gwClient = GatewayClient("localhost", GatewaySettings.PORT_CLIENT)
        gwClient.startEpoll(ClientHandler())
    }.start()
}