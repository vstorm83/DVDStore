<%-- 
    Document   : RightSidebar
    Created on : Feb 5, 2010, 3:24:36 AM
    Author     : VU VIET PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="dvdstore" uri="/dvdstore-tags" %>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td>
        <dvdstore:ShoppingCartWindow itemTitle="itemTitle.ShoppingCart" >
            <dvdstore:ShoppingCartSymbol shoppingCartKey="shoppingCart.label" 
                                         shoppingCartButtonKey="shoppingCart.button.label"
                                         unit="shoppingCart.unit" itemCount="shoppingCartItemCount" />
        </dvdstore:ShoppingCartWindow>        
    </td>
  </tr>
  <tr>
      <td>
          <dvdstore:ChatWindow itemTitle="itemTitle.Chat" >
              <dvdstore:ChatList list="chatList" >
                  <dvdstore:ChatCell chatNick="nickName" />
              </dvdstore:ChatList>
          </dvdstore:ChatWindow>
    </td>
  </tr>
  <tr>
    <td>        
        <dvdstore:SearchWindow itemTitle="itemTitle.Search" >
            <%--<s:form action="" namespace="" method="GET" >
                <s:textfield key="movieName" size="20" maxlength="50" labelposition="top"/>
                <s:textfield key="actorName" size="12" maxlength="50" labelposition="top"/>
                <s:url value="/struts/defaultTheme/images/b_search.png" var="b_search.png" />
                <s:submit type="image" src="%{b_search.png}" 
                          required="true" label="Phương" value="tét" />
            </s:form>--%>
            <dvdstore:SearchForm movieNameKey="movName" actorNameKey="actName"/>
        </dvdstore:SearchWindow>
    </td>
  </tr>
  <tr>
    <td>
        <dvdstore:BestMovieWindow itemTitle="itemTitle.BestMovie" >
            <dvdstore:BestMovieList list="bestMovieList" >
                <dvdstore:BestMovieCell movieId="movId" movieName="movName" moviePictureUrl="movPicUrl" />
            </dvdstore:BestMovieList>
        </dvdstore:BestMovieWindow>
    </td>
  </tr>
</table>
