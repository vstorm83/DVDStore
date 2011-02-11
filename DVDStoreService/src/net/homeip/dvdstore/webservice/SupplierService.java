/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface SupplierService {

    List<SupplierVO> getSupplierList();

    SupplierVO getSupplierById(long supplierId);

    public void addSupplier(SupplierVO supplierVO)
            throws InvalidAdminInputException, DuplicateException;

    public void deleteSupplier(List<Long> supplierIdList, boolean ignoreReference)
            throws DBReferenceViolationException;

    public void updateSupplier(SupplierVO supplierVO)
            throws InvalidAdminInputException, DuplicateException;
}
