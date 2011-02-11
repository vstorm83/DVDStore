// JavaScript Document
function testLogIn()
    {
	  loi="";
	  if(document.frmLogIn.txtUserName.value=="")
	    loi+="Phai nhap UserName \n";
	  if(document.frmLogIn.txtPassWord.value=="")
	    loi+="Phai nhap PassWord";
	  if(loi!="")
   	    alert(loi);
      else
	    {
		  if(document.frmLogIn.chkGhiNho.checked==true)
   	    	{  
      	  time= new Date();
	  	  time.setDate(time.getDate()+10)
      	  document.cookie= "txtUserName=" + document.frmLogIn.txtUserName.value + "; expires=" + time.toUTCString() + "; path=/"
      	  document.cookie= "txtPassWord=" + document.frmLogIn.txtPassWord.value + "; expires=" + time.toUTCString() + "; path=/"
		    }
		  document.frmLogIn.submit();
		}    
	}
	
function xoaCookie()
    {
	  time= new Date();
	  time.setDate(time.getDate()-1)
      document.cookie= "txtUserName= " + "; expires=" + time.toUTCString() + "; path=/"
      document.cookie= "txtPassWord= " + "; expires=" + time.toUTCString() + "; path=/"
	}