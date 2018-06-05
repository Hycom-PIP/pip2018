new Vue({
    el: '#root',
    data() {
        return {
            query: null
        }
    },
    methods: {
        handleSearchButtonClicked: function () {
            // noinspection JSUnusedGlobalSymbols
            axios
                .get('/results?query=' + this.query)
                .then(response => (this.numberOfViews = response.data));
        }
    }
});