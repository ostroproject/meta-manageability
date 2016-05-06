var pageDef = {
    name: "ethernet",
    resource: "/local/device/ethernet",
    title: "Wired network configuration",
    fields: {
        Enable: {
            desc: "enable/disable wired networking", 
            type: "checkbox",
            defval: "true"
        },
        IPv4: {
            type: "section",
            value: {
                desc: "basic IPv4 operation mode",
                type: "select",
                options: {
                    DHCP: "DHCP",
                    "-": "fixed",
                    Off: "disabled"
                },
                events: {
                    change: onIPv4Change
                }
            },
            defval: "DHCP",
            fields: {
                IPAddress: {
                    desc: "IPv4 address in dotted form, eg. 192.168.1.1",
                    type: "text",
                    size: 11,
                    pattern: dottedIP4Pattern()
                },
                SubnetMask: {
                    desc: "network mask in dotted form, eg. 255.255.255.0",
                    type: "text",
                    size: 11,
                    pattern: dottedIP4Pattern()
                },
                Gateway: {
                    desc: "gateway (router) for the device in dotted form",
                    type: "text",
                    size: 11,
                    pattern: dottedIP4Pattern()
                },
            }
        }
    }
}

function onIPv4Change() {
    var mode = this.value
    var disabled = (mode != "-")

    setInput("wifiIPv4IPAddress", disabled, true)
    setInput("wifiIPv4SubnetMask", disabled, true)
    setInput("wifiIPv4Gateway", disabled, true)
}
