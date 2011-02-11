/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
    //CHỈ CHO NHẬP SỐ VÀO SỐ LƯỢNG HÀNG
function ChiNhapSo(id)
{
    re=/^\d{1,2}$/
    s=document.getElementById(id).value;
    if(re.test(s)==false)
    {
        s=s.replace(/[^0-9]/gi,"")
        document.getElementById(id).value=s;
    }
}

