<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="dvdstore" uri="/dvdstore-tags" %>    
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td>
        <dvdstore:MovieCatgoryWindow itemTitle="itemTitle.MovieCatgory">
            <dvdstore:MovieCatgoryList list="movieCatgoryList" >
                <dvdstore:MovieCatgoryCell movieCatgoryId="movCatId" movieCatgoryName="movCatName" />
            </dvdstore:MovieCatgoryList>
        </dvdstore:MovieCatgoryWindow>
    </td>
  </tr>
  <tr>
      <td>
        <tiles:insertAttribute name="loginSidebarItem" />
    </td>
  </tr>
  <%--<tr>
    <td>
        <dvdstore:AdvertismentWindow itemTitle="itemTitle.Advertisment">
            <dvdstore:AdvertismentList list="advertismentList" >
                <dvdstore:AdvertismentCell advertismentSource="adSource" advertismentLink="adLink" />
            </dvdstore:AdvertismentList>
        </dvdstore:AdvertismentWindow>
    </td>
  </tr>--%>
</table>