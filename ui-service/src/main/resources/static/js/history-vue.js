new Vue({
    el: '#root',
    data() {
        return {
            rows: null,
        }
    },
    mounted() {
        this.fill()
    },
    methods: {
        fill: function () {
            axios
                .get('/history-data')
                .then(response => (this.rows = response.data))
        }
    }
})