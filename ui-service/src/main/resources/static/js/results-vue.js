$(document).ready(function(){
    $('#link-to-home').addClass('active');
});

// noinspection JSUnusedGlobalSymbols
new Vue({
    el: '#root',
    data() {
        return {
            results: null,
        }
    },
    mounted() {
        this.fill()
    },
    methods: {
        fill: function () {
            // noinspection JSUnusedGlobalSymbols
            axios
                .get('/query-data?query=' + $route.query.query)
                .then(response => (this.results = response.data));
        }
    }
});