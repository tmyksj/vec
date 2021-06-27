window.addEventListener("DOMContentLoaded", () => {
    const register = (() => {
        const registryObj = {};

        return (name, builder) => {
            builder({
                handle: (type, handler) => {
                    if (registryObj[type] === undefined) {
                        registryObj[type] = [];

                        document.addEventListener(type, (event) => {
                            registryObj[type].forEach((registry) => {
                                const store = {};

                                for (let element = event.target; element !== null; element = element.parentElement) {
                                    registry.handler({
                                        current: element,
                                        findAllByAttribute: (attribute) => {
                                            return document.querySelectorAll(`[${attribute}]`);
                                        },
                                        findByAttribute: (attribute) => {
                                            return document.querySelector(`[${attribute}]`);
                                        },
                                        isRoot: element.parentElement === null,
                                        store: store,
                                    });
                                }
                            });
                        });
                    }

                    registryObj[type].push({
                        handler: handler,
                        name: name,
                    });
                },
                name: name,
            });
        };
    })();

    register("vec-autoclose", (builder) => {
        builder.handle("click", (context) => {
            context.store.excludeList ||= [];

            if (context.current.hasAttribute(builder.name)) {
                const attribute = context.current.getAttribute(builder.name);
                context.store.excludeList.push(attribute);
            }

            if (context.isRoot) {
                context.findAllByAttribute(builder.name).forEach((element) => {
                    if (!context.store.excludeList.includes(element.getAttribute(builder.name))) {
                        element.classList.add("hidden");
                    }
                });
            }
        });
    });

    register("vec-open", (builder) => {
        builder.handle("click", (context) => {
            if (context.current.hasAttribute(builder.name)) {
                const attribute = context.current.getAttribute(builder.name);
                context.findByAttribute(`vec-id="${attribute}"`).classList.remove("hidden");
            }
        });
    });
});
