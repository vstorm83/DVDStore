<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib  prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="dvdstore" uri="/dvdstore-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <s:head />
    </head>

    <body>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td rowspan="4" class="backgroundImage">&nbsp;</td>
                <td colspan="3"><dvdstore:TopBanner /></td>
                <td rowspan="4" class="backgroundImage">&nbsp;</td>
            </tr>
                <tr>
                <td colspan="3"><dvdstore:TopMenu main="button.Home" contact="button.Contact"
                                  guide="button.Guide" register="button.Register"/></td>
            </tr>
            <tr>
                <td width="150" valign="top"><tiles:insertAttribute name="leftSidebar"/></td>
                <td width="486" valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><dvdstore:SearchBar /></td>
                        </tr>
                        <tr>
                            <td><tiles:insertAttribute name="bodyContent" /></td>
                        </tr>
                    </table>
                </td>
                <td width="150" valign="top"><tiles:insertAttribute name="rightSidebar" /></td>
            </tr>
            <tr>
                <td colspan="3"><dvdstore:BottomMenu info="comName"/></td>
            </tr>
        </table>
    </body>
</html>