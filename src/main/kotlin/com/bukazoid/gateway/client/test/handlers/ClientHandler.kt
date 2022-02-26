package com.bukazoid.gateway.client.test.handlers

import com.bukazoid.gateway.client.AClientHandler
import com.bukazoid.gateway.client.test.common.Common
import com.bukazoid.gateway.client.test.common.TestChannelMessage
import com.bukazoid.gateway.client.test.common.TestHelloMessage
import com.bukazoid.gateway.client.test.common.TestLoadMessage
import com.bukazoid.gateway.common.logger
import com.bukazoid.gateway.messages.system.SystemMessage
import com.bukazoid.gateway.messages.system.client.ChannelSubscribed
import com.bukazoid.gateway.messages.system.client.ChannelSubscriptionFailed
import com.bukazoid.gateway.messages.system.client.SubscribeChannel
import io.netty.channel.ChannelHandlerContext

open class ClientHandler : AClientHandler() {
    private val log = logger()


    override fun onSysMessage(msg: SystemMessage) {
        log.info("sys message: {}", msg)
        if (msg is ChannelSubscriptionFailed) {
            log.error("subscription is failed")
        } else if (msg is ChannelSubscribed) {
            doLoad()
        }
    }

    override fun onClientMessage(channel: String, msgBytes: ByteArray) {
        if (channel != Common.CHANNEL_NAME) {
            log.warn("not test channel message")
            return
        }
        log.info("test channel message")
        log.info("message: {}", mapper.readValue(msgBytes, TestChannelMessage::class.java))
    }

    private fun doLoad() {
        if (super.ctx == null) {
            log.error("ctx is not initialized, no connection")
            return
        }

        writeChannelFlush(
            Common.CHANNEL_NAME,
            TestHelloMessage("hello master")
        )

        for (i in 1..1_000_000L) {
            writeChannel(
                Common.CHANNEL_NAME,
                TestLoadMessage(i, "hello, it is me. yes, again me")
            )
        }

        flush()
    }

    override fun onConnect(id: String, ctx: ChannelHandlerContext) {
        log.info("Channel Active with with ChannelID: " + id(ctx))
        log.info("subscribe to channel")
        writeSysFlush(SubscribeChannel(Common.CHANNEL_NAME))
    }

    override fun onDisconnect(id: String) {
        super.onDisconnect(id)
    }

}