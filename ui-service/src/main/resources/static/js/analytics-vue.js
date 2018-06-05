new Vue({
    el: '#root',
    data() {
        return {
            numberOfViews: null,
            rows: null,
            timePeriod: null
        }
    },
    mounted() {
        this.fill('3months')
    },
    methods: {
        fill: function (message) {
            // noinspection JSUnusedGlobalSymbols
            axios
                .get('/test?period=' + message)
                .then(response => (this.numberOfViews = response.data));
            // noinspection JSUnusedGlobalSymbols
            axios
                .get('/statistics?period=' + message)
                .then(response => (this.rows = response.data));
            if (message === '7days') {
                // noinspection JSUnusedGlobalSymbols
                this.timePeriod = 'Last 7 days'
            } else if (message === '30days') {
                // noinspection JSUnusedGlobalSymbols
                this.timePeriod = 'Last 30 days'
            } else if (message === '3months') {
                // noinspection JSUnusedGlobalSymbols
                this.timePeriod = 'Last 3 months'
            }
        }
    }
});