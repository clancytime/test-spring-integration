beans {
    xmlns integration:"http://www.springframework.org/schema/integration"

    integration.'wire-tap'(id: 'channelTesting', pattern: 'channelSuccess', order: 1, channel: 'testChannel')
    integration.channel(id: 'testChannel'){
        integration.queue()
    }


}