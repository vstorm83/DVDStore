<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
    <tbody>
    <tr>
      <td colspan="2" height="3"></td>
    </tr>
    <tr>
	  <td colspan="2">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
		  <tbody><tr>
			  <td align="left" class="movieDetailName">
				&nbsp; <@s.property value="${parameters.movieName}" />
			  </td>
			  <td align="right">
				<@s.form action="${parameters.action!'PlaceOrder_add'}" namespace="${parameters.namespace!'/movie'}"
                                    theme="simple" method="get">
                                    <@s.hidden name="${parameters.movieId}" value="${stack.findString(parameters.movieId)}" />
                                    <@s.submit cssClass="orderButton"
                                        value="${parameters.placeOrderButtonLabel}" />
				</@s.form>
			  </td>
		  </tr>
		</tbody></table>
	  </td>
    </tr>
    <tr>
      <td colspan="2" height="5"></td>
    </tr>
    <tr>
      <td width="205px" valign="middle" align="center">
	    <img height="240px" width="166px" <#t/>
                src="<@s.url value='${stack.findString(parameters.moviePictureUrl)!""}' encode='false'/>">
      </td>
      <td width="277px">
<!-- BẢNG CON THÔNG TIN PHIM -->
		  <table width="263px" cellspacing="0" cellpadding="4" border="0">
			<tbody><tr>
			  <td width="77px" align="left" class="smallMovieDetailTable">
                            ${(parameters.directorLabel!"")?xhtml}:</td>
			  <td width="170px">
			    <@s.property value="${parameters.directorKey}" />
			  </td>
			</tr>
			<tr>
			  <td align="left" class="smallMovieDetailTable">
                            ${(parameters.actorLabel!"")?xhtml}:</td>
			  <td>
                            <#list stack.findValue(parameters.actorKey)![] as actor >
                                ${(actor!"")?xhtml}<#if actor_has_next>, </#if>
                            </#list>
			  </td>
			</tr>
			<tr>
			  <td align="left" class="smallMovieDetailTable">
                            ${(parameters.movieCatgoryLabel!"")?xhtml}:</td>
			  <td>
                            <@s.property value="${parameters.movieCatgoryKey}" />
                          </td>
			</tr>
			<tr>
			  <td align="left" class="smallMovieDetailTable">
                            ${(parameters.productPriceLabel!"")?xhtml}:</td>
			  <td class="movieDetailMoney">
                            <@s.property value="${parameters.productPriceKey}" /> <#t/>
                            ${(parameters.moneyUnit!"")?xhtml}</td>
			</tr>
                        <tr>
			  <td align="left" class="smallMovieDetailTable">
                            ${(parameters.saleOffLabel!"")?xhtml}:</td>
			  <td class="movieDetailMoney">
                            <@s.property value="${parameters.saleOffKey}" /> %<#t/>
                          </td>
			</tr>
	    </tbody></table>
<!-- KẾT THÚC BẢNG CON THÔNG TIN PHIM -->
	  </td>
    </tr>
    <tr>
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="2">
	  	<!-- BẮT ĐẦU BẢNG MÔ TẢ -->
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
		  <tbody><tr>
		    <td width="5">&nbsp;</td>
		    <td class="movieDetailDescription">
		      	${stack.findString(parameters.productDescription)!""}
		    </td>
		    <td width="3">&nbsp;</td>
	 	  </tr>
		</tbody></table>
		<!-- KẾT THÚC BẢNG MÔ TẢ -->
	  </td>
    </tr>
</tbody></table>