<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" colspan="3"></td>
  </tr>
  <tr>
    <td width="3"></td>
    <td>
    <table width="100%" cellspacing="0" cellpadding="0" border="0">
      <tbody><tr>
        <td>
            <div align="center" class="contacCompanyName">
                ${(parameters.companyName!"")?xhtml}
            </div><br>
            <table cellspacing="0" cellpadding="0" border="0">
              <tbody><tr>
                <td>&nbsp;</td>
                <td class="contactLabel">
                    ${(parameters.addressLabel!"")?xhtml}:</td>
                <td>&nbsp;</td>
                <td>
                    ${(parameters.address!"")?xhtml}
                </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td class="contactLabel">
                    ${(parameters.telLabel!"")?xhtml}:
                </td>
                <td>&nbsp;</td>
                <td>
                    ${(parameters.tel!"")?xhtml}
                </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td class="contactLabel">
                    ${(parameters.emailLabel!"")?xhtml}:
                </td>
                <td>&nbsp;</td>
                <td>
                    ${(parameters.email!"")?xhtml}
                </td>
              </tr>
            </tbody></table>
        </td>
      </tr>
    </tbody></table>
    </td>
    <td></td>
  </tr>
  <tr>
    <td height="2" colspan="3"></td>
  </tr>
</table>