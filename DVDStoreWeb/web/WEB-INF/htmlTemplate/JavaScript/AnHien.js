// JavaScript Document
  function shAll(s)
    {
	  s.backgroundColor="#ccccff"
	  n= document.getElementById("SoBang").innerHTML;
	  if(s.backgroundColor=="#9999ff" || s.fontWeight=="bold")
	    {
			for(i=1;i<=n;++i)
			  {
				document.getElementById(i).style.display="block";
			  }
			s.backgroundColor="#ccccff"
			s.fontWeight="normal"
		}
	  else
	    {
			for(i=1;i<=n;++i)
			  {
				document.getElementById(i).style.display="none";
			  }
			s.backgroundColor="#9999ff"		 
			s.fontWeight="bold" 			
		}	
	}
  function sh(i)
    {
	  if(document.getElementById(i).style.display=="none")
	    document.getElementById(i).style.display="block";
	  else
	    document.getElementById(i).style.display="none";	    
	}
  function shTimKiem(tbl)
    {
	  tt=document.getElementById(tbl).style.display;
	  if(tt=="none")
	    {
		  document.getElementById("btnAnHienTimKiem").value="Dấu tìm kiếm";
		  document.getElementById(tbl).style.display="block";
		  document.getElementById("NutAnHienTimKiem").value="Dấu tìm kiếm";
		  document.getElementById("BangTimKiem").value="block";
		}
	  else
	    {
		  document.getElementById("btnAnHienTimKiem").value="Hiện tìm kiếm";
		  document.getElementById(tbl).style.display="none";
		  document.getElementById("NutAnHienTimKiem").value="Hiện tìm kiếm";
		  document.getElementById("BangTimKiem").value="none";
		}
	}
		
