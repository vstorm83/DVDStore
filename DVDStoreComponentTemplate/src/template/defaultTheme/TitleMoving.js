function move_title(i,j)
{
    msg="Dịch vụ bán & Cho thuê DVD online đầu tiên tại Hà Nội... Dịch vụ bán & Cho thuê DVD online đầu tiên tại Hà Nội... ";
    out=msg.substr(i,j);
    document.title=out;
    //window.status=out;
    i++;
	 
    if(i==57)
    {
        i=0;
    }
    window.setTimeout("move_title("+i+","+j+")",300);
}
function MM_preloadImages() { //v3.0
    var d=document;
    if(d.images){
        if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments;
        for(i=0; i<a.length; i++)
            if (a[i].indexOf("#")!=0){
                d.MM_p[j]=new Image;
                d.MM_p[j++].src=a[i];
            }
        }
}
window.onload = move_title(0,57);