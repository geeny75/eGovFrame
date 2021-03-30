
	
	/*코로나19 현황 공공데이터 API 호출 */
	function fCovid19InfStateJsonJsp(){
		var serviceKeyEn = "zZmvp8orLEvNI31rbrzuGVtdLvVZ3juLI%2BcH7%2BXyjpCuIqJeSzwN2gWYsf6KJ5AZVUxnjLN2sM24VcNfUsbwow%3D%3D"; 
		var serviceKey = "zZmvp8orLEvNI31rbrzuGVtdLvVZ3juLI+cH7+XyjpCuIqJeSzwN2gWYsf6KJ5AZVUxnjLN2sM24VcNfUsbwow==";
		var today = getToday();
		
		var xhr = new XMLHttpRequest();
		 
		
		var url = 'http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson'; /*URL*/
		
		//var queryParams = '?' + encodeURIComponent('ServiceKey') + '='+encodeURIComponent(serviceKey); /*Service Key*/
		var queryParams = '?' + encodeURIComponent('ServiceKey') + '='+ serviceKeyEn; /*Service Key*/
		//queryParams += '&' + encodeURIComponent('pageNo') + '=' + encodeURIComponent('1'); /**/
		//queryParams += '&' + encodeURIComponent('numOfRows') + '=' + encodeURIComponent('10'); /**/ 
		queryParams += '&' + encodeURIComponent('pageNo') + '=' + encodeURIComponent('1'); /**/
		queryParams += '&' + encodeURIComponent('numOfRows') + '=' + encodeURIComponent('10'); /**/
		queryParams += '&' + encodeURIComponent('startCreateDt') + '=' + encodeURIComponent(today); /**/
		queryParams += '&' + encodeURIComponent('endCreateDt') + '=' + encodeURIComponent(today); /**/
		console.log('▶ url : ' + url +  queryParams);
		
		xhr.open('GET', url + queryParams);
		xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
		
		xhr.onreadystatechange = function () {
			console.log('▶ readyState : ' +  this.readyState);
			console.log('▶ status : ' +  this.status);
			console.log('▶ statusText : ' +  this.statusText);
		    if (this.readyState == xhr.DONE && xhr.status === 200) { 
		    	var header = this.getAllResponseHeaders();
				console.log('▶ header : ' + header );
				console.log('▶ responseText :' + this.responseText); 
				console.log('▶ responseXML :' + this.responseXML);  
				console.log('▶ response(body) :' + this.response);  
			
		      //  alert('Status: '+this.status+'nHeaders: '+JSON.stringify(this.getAllResponseHeaders())+'nBody: '+this.responseText);
		    }
		};
		
		xhr.send('');
		
		document.getElementById("todayCnt").innerHTML = 1;
		//document.getElementById("domesticCnt").innerHTML = 1;
		//document.getElementById("overseasCnt").innerHTML = 1; 
	}
	
	function getToday(){ 
	    var date = new Date();
	    var year = date.getFullYear();
	    var month = ("0" + (1 + date.getMonth())).slice(-2);
	    var day = ("0" + date.getDate()).slice(-2);
	
	    return year + month + day;	 
	}
	
	