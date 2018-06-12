function getSearchParams() {
	var url = new URL(window.location.href);	
	
	var providers = [];        	
	$.each(url.searchParams.getAll("provider"), function( index, el ){
		providers=providers.concat(el.split(','));
	});
	
	return {
		query: url.searchParams.get("query"),
		provider: providers
	};
}

$(document).ready(function(){
	$('#link-to-home').addClass('active');
	
	var searchParams = getSearchParams()
	$("#query").val(searchParams.query);
	
	
	$('.provider-checkbox').each(function( index, el ){
		var selected = searchParams.provider.indexOf($(el).val()) != -1;
		$(el).prop('checked', selected);
		if (selected) {
			$(el).parent('.btn').addClass('active');			
		} else {
			$(el).parent('.btn').removeClass('active');			
		}
	});
	
});

new Vue({
    el: '#root',
    data() {
        return {
            result: {},
        }
    },
    mounted() {
        this.fill()
    },
    methods: {
    	fill: function () {
        	var searchParams = getSearchParams();   
            axios
                .get('/query-data', {params: {query: searchParams.query, provider: searchParams.provider.join()}})
                .then(response => (this.result = response.data))
        },
        submit: function (submitEvent) {
        	submitEvent.preventDefault();
        	
        	var providers = [];        	
        	$.each(submitEvent.target.elements.provider, function( index, el ){
        		if ($(el).prop('checked')) {        			
        			providers.push(encodeURIComponent(el.value));
        		}
        	});
        	
        	window.history.pushState('', '', new URL(window.location.href).pathname + '?query=' + encodeURIComponent(submitEvent.target.elements.query.value) + '&provider=' + providers.join());
        	gtag('config', GTAG_ID, {page_path: window.location.href, page_location: window.location.href});
        	
        	axios
	            .get('/query-data', {params: {query: submitEvent.target.elements.query.value, provider: providers.join()}})
	            .then(response => (this.result = response.data))
        }
    }
})