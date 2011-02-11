<%-- 
    Document   : MovieDetail
    Created on : Feb 18, 2010, 4:40:54 PM
    Author     : VU VIET PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="dvdstore" uri="/dvdstore-tags" %>
<dvdstore:CenterWindow itemTitle="itemTitle.MovieDetail" >
    <dvdstore:MovieDetailCell movieId="movId" movieName="movName" moviePictureUrl="movPicUrl"
                              placeOrderButtonKey="movieDetailCell.placeOrderButton"
                              directorKey="movDirector" actorKey="movActor"
                              movieCatgoryKey="movCat" productPriceKey="movPrice"
                              saleOffKey="movSaleOff" moneyUnit="movieDetailCell.moneyUnit"
                              productDescription="movDesc" />
</dvdstore:CenterWindow>
