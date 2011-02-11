// JavaScript Document
	function FloatTopDiv()
	{
		startX = document.body.clientWidth - 110, startY = 71;
		var ns = (navigator.appName.indexOf("Netscape") != -1);
		var d = document;
			
		if (document.body.clientWidth < 980) startX = -110;

		
		function ml(id)
		{
			var el=d.getElementById?d.getElementById(id):d.all?d.all[id]:d.layers[id];
			if(d.layers)el.style=el;
			el.sP=function(x,y){this.style.left=x;this.style.top=y;};
			el.x = startX;
			el.y = startY;
			return el;
		}
		
		window.stayTopLeft=function()
		{
		
			if (document.body.clientWidth < 980)
			{
				ftlObj.x = - 115;ftlObj.y = 0;	ftlObj.sP(ftlObj.x, ftlObj.y);
			}
			
			else
			{
			if (document.documentElement && document.documentElement.scrollTop)
				var pY = ns ? pageYOffset : document.documentElement.scrollTop;
			else if (document.body)
				var pY = ns ? pageYOffset : document.body.scrollTop;

			if (document.body.scrollTop > 71){startY = 3} else {startY = 71};

			if (document.body.clientWidth >= 1024)
			{
				ftlObj.x = document.body.clientWidth - 110;ftlObj.y += (pY + startY - ftlObj.y)/32;ftlObj.sP(ftlObj.x, ftlObj.y);
			}
			else
			{
			
			
			ftlObj.x  = startX;
			ftlObj.y += (pY + startY - ftlObj.y)/32;
			ftlObj.sP(ftlObj.x, ftlObj.y);
			}
			}
			setTimeout("stayTopLeft()", 1);
		}
		
		ftlObj = ml("divAdRight");
		stayTopLeft();
		
	}
function FloatTopDiv2()
	{
		startX2 = document.body.clientWidth - 1004, startY2 = 71;
		var ns2 = (navigator.appName.indexOf("Netscape") != -1);
		var d2 = document;
			
		if (document.body.clientWidth < 980) startX2 = -110;

		
		function ml2(id)
		{
			var el2=d2.getElementById?d2.getElementById(id):d2.all?d2.all[id]:d2.layers[id];
			if(d2.layers)el2.style=el2;
			el2.sP=function(x,y){this.style.left=x;this.style.top=y;};
			el2.x = startX2;
			el2.y = startY2;
			return el2;
		}
		
		window.stayTopLeft2=function()
		{
			if (document.body.clientWidth < 980)
			{
				ftlObj2.x = - 115;ftlObj2.y = 0;	ftlObj2.sP(ftlObj2.x, ftlObj2.y);
			}
			else
			{
			if (document.documentElement && document.documentElement.scrollTop)
				var pY2 = ns2 ? pageYOffset : document.documentElement.scrollTop;
			else if (document.body)
				var pY2 = ns2 ? pageYOffset : document.body.scrollTop;

			if (document.body.scrollTop > 71){startY2 = 3} else {startY2 = 71};

			if (document.body.clientWidth >= 1024)
			{
				ftlObj2.x =  0;ftlObj2.y += (pY2 + startY2 - ftlObj2.y)/32;	ftlObj2.sP(ftlObj2.x, ftlObj2.y);
			}
			else
			{			
			

			
			ftlObj2.x  = startX2;
			ftlObj2.y += (pY2 + startY2 - ftlObj2.y)/32;
			ftlObj2.sP(ftlObj2.x, ftlObj2.y);
			}
			}
			setTimeout("stayTopLeft2()", 1);
		}
		
		ftlObj2 = ml2("divAdLeft");
		stayTopLeft2();
		
	}


	function ShowAdDiv()
	{
		var objAdDivLeft  = document.getElementById("divAdLeft");
		var objAdDivRight = document.getElementById("divAdRight");
		if (document.body.clientWidth < 980)
		{
			objAdDivLeft.style.left  = - 110;
			objAdDivRight.style.left = - 110;
		}
		
		else
		{
			objAdDivLeft.style.left  = 0;
			objAdDivRight.style.left = document.body.clientWidth - 110;
		}
		FloatTopDiv();
		FloatTopDiv2();
	}
	ShowAdDiv();
