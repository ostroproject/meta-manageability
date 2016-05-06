var pageDef = {
    name: "wifi",
    resource: "/local/device/wifi",
    title: "WLAN configuration",
    fields: {
        Enable: {
            desc: "enable/disable the WLAN interface", 
            type: "checkbox",
            defval: "true"
        },
        Mode: {
            desc: "role of the device",
            type: "select",
            options: {
                AccessPoint: "access point",
                EndPoint: "end point",
                Tether: "tether"
            },
            defval: "EndPoint"
        },
        Name: {
            desc: "name (SSID) of the WiFi network",
            type: "text",
            size: 32,
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
        },
        Security: {
            type: "section",
            fields: {
                Mode: {
                    desc: "WLAN message encryption type",
                    type: "select",
                    options: {
                        WEP: "WEP",
                        "WPA2-private": "WPA2 private",
                    },
                    defval: "WPA2-private"
                },
                PreSharedKey: {
                    desc: "passphrase for the selected encryption type",
                    type: "password",
                    size: 64
                }
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
