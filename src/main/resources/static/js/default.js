let Vue = null;

window.vec = {
    mixins: [],

    main() {
        document.querySelectorAll("#v-app script").forEach((value) => {
            value.remove();
        });
        Vue.createApp({mixins: this.mixins}).mount("#v-app");
    },

    mixin(mixin) {
        this.mixins.push(mixin);
    },
};

Promise.all([
    new Promise((resolve) => {
        window.addEventListener("DOMContentLoaded", () => {
            resolve();
        });
    }),
    fetch("/js/vue.global.prod.js").then((response) => {
        return response.text();
    }).then((response) => {
        Vue = new Function(response + "return Vue;")();
    }),
]).then(() => {
    const vec = window.vec;
    delete window.vec;

    vec.main();
});
