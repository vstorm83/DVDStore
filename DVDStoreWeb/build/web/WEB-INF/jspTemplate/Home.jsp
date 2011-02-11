<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="dvdstore" uri="/dvdstore-tags" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">    
    <tr>
        <td>
            <dvdstore:NewestMovie movie="newestMovie" movieId="movId" movieName="movName"
                          movieDescription="movDesc" moviePictureUrl="movPicUrl" />
        </td>
    </tr>
    <tr>
        <td>
            <dvdstore:CenterWindow itemTitle="itemTitle.NewMovieWindow" >
                <dvdstore:MovieGrid list="newMovieList" >
                    <dvdstore:MovieCell movieId="movId" movieName="movName" moviePictureUrl="movPicUrl" />
                </dvdstore:MovieGrid>
            </dvdstore:CenterWindow>
        </td>
    </tr>
</table>