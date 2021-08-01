let Vue = null;

window.vec = {
    mixins: [
        {
            data: {
                rd: [],
            },
        },
    ],

    main() {
        document.querySelectorAll("#v-app script").forEach((value) => {
            value.remove();
        });

        Vue.createApp({ mixins: this.mixins }).mount("#v-app");
    },

    mixin(mixin) {
        this.mixins.push(mixin);
    },

    rd(data) {
        this.mixins[0].data.rd.push(data);
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
