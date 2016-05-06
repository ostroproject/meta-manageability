var pageDef = {
    name: "ssh",
    resource: "/local/devel/ssh",
    title: "Secure Shell login Configuration",
    fields: {
        Enable: {
            desc: "enable/disable secure shell logins", 
            type: "checkbox",
            defval: "true"
        },
        Port: {
            desc: "ssh port",
            type: "number",
            size: 5,
            min: 1,
            max: 65535,
            defval: "22"
        },
    }
}
