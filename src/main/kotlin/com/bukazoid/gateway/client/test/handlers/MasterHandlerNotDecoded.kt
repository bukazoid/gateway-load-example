package com.bukazoid.gateway.client.test.handlers

import com.bukazoid.gateway.client.AMasterHandler
import com.bukazoid.gateway.client.test.common.Common
import com.bukazoid.gateway.client.test.common.TestChannelMessage
import com.bukazoid.gateway.common.logger
import com.bukazoid.gateway.common.settings.GatewaySettings
import com.bukazoid.gateway.messages.GatewayServerMessage
import com.bukazoid.gateway.messages.system.SystemMessage
import com.bukazoid.gateway.messages.system.master.ChannelRegistered
import com.bukazoid.gateway.messages.system.master.RegisterChannel
import io.netty.channel.ChannelHandlerContext

open class MasterHandlerNotDecoded : AMasterHandler() {
    private val log = logger()

    private var startTime: Long = 0

    override fun onSysMessage(msg: SystemMessage) {
        if (msg is ChannelRegistered) {
            log.info("channel {} is registered", msg.name)
        } else {
            log.info("incoming message: {}", msg)
        }
    }

    override fun onClientMessage(channel: String, msgBytes: ByteArray) {
        log.error("incorrect message for channel: {}", channel)
    }

    override fun onServerMessage(channel: String, clientId: String, msgBytes: ByteArray) {
        if (channel != Common.CHANNEL_NAME) {
            log.warn("not supported channel: $channel")
            return
        }
        readServerMessage(toMessage(msgBytes, TestChannelMessage::class.java), clientId)
    }

    override fun onConnect(id: String, ctx: ChannelHandlerContext) {
        super.onConnect(id, ctx)
        ctx.writeAndFlush(
            GatewayServerMessage(
                GatewaySettings.SYSTEM_CHANNEL, GatewaySettings.SYSTEM_CHANNEL,
                toBytes(RegisterChannel(Common.CHANNEL_NAME, "hardcoded auth token"))
            )
        )
    }

    override fun onDisconnect(id: String) {
        super.onDisconnect(id)
    }

    val PIECE_SIZE = 200_000L
    var currentMessageIndex = 0;

    fun readServerMessage(msg: TestChannelMessage, clientId: String) {
        val now = System.currentTimeMillis()

        currentMessageIndex++;
        if (currentMessageIndex % PIECE_SIZE == 1L) {
            log.info("START not decoded messages")
            startTime = System.currentTimeMillis()
        }
        if (currentMessageIndex % PIECE_SIZE == 0L) {
            val duration = now - startTime
            log.info(
                "DONE! in {} ms, messages/second(mps): {} bytes: {}",
                duration,
                PIECE_SIZE * 1000 / duration,
                this.bytesReceived
            )
        }

    }

}