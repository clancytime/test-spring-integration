beans {
    xmlns integration:"http://www.springframework.org/schema/integration"

    integration.channel(id:"channelError")
    integration.chain('input-channel': 'channelError') {
        integration.'recipient-list-router'() {
            integration.recipient(channel: 'channelErrorMessage')
            integration.recipient(channel: 'channelSuccess')
        }
    }

    integration.channel(id:"channelIgnoreError")
    integration.chain('input-channel': 'channelIgnoreError') {
        integration.'recipient-list-router'('ignore-send-failures': 'true') {
            integration.recipient(channel: 'channelErrorMessage')
            integration.recipient(channel: 'channelSuccess')
        }
    }

    integration.transformer('input-channel':'channelErrorMessage', expression:'payload.invalidHeader')

    integration.'logging-channel-adapter'(id: 'channelSuccess','level': 'INFO', 'expression': '"Message successfully added"')
}