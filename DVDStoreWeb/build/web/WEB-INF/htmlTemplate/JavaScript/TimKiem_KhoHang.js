// JavaScript Document
  function disTimKiem(tk1,tk2,i)
    {
	  if(i==0)
	    {
		  if(tk1!="")
		    {
			  document.getElementById(tk1).disabled=true;
			}
		  if(tk2!="")
		    {
			  document.getElementById(tk2).disabled=true;
			}
		}
	  else
	    {
		  if(tk1!="")
		    {
			  document.getElementById(tk1).disabled=false;
			}
		  if(tk2!="")
		    {
			  document.getElementById(tk2).disabled=false;
			}
		}
	}
  function testTimKiem()
    {
	  for(i=0;i<document.frmQuanLyKhoHang.slcThuTu.length;++i)
	    {
		  if(document.frmQuanLyKhoHang.slcThuTu[i].value==1)
		    break;		  
		}
	  if(i<document.frmQuanLyKhoHang.slcThuTu.length)
	    return true;
	  else 
	    {
		  alert("Bạn phải chọn 1 điều kiện với thứ tự ưu tiên bằng 1");
		  return false;
		}
	}
  //CHỈ CHO NHẬP SỐ
  function ChiNhapSo(id)
	{
	  re=/^\d+$/
	  s=document.getElementById(id).value;	    			  
	  if(re.test(s)==false)
		{						  	  
		  s=s.replace(/[^0-9]/gi,"")
		  document.getElementById(id).value=s;
		}
	}	