<%-- 
    Document   : NewSearchResult
    Created on : Feb 7, 2010, 11:16:14 PM
    Author     : VU VIET PHUONG
--%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="dvdstore" uri="/dvdstore-tags" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>
            <dvdstore:CenterWindow label="%{movCatName}" itemTitle="itemTitle.SearchResult" >
                <dvdstore:MovieGrid list="movieSearchResultList" >
                    <dvdstore:MovieCell movieId="movId" movieName="movName" moviePictureUrl="movPicUrl" />
                </dvdstore:MovieGrid>
            </dvdstore:CenterWindow>
        </td>
    </tr>
    <tr>
        <td>
            <dvdstore:Paging />
        </td>
    </tr>
</table>
