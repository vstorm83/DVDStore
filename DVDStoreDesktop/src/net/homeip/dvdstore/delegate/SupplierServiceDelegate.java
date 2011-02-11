/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import java.util.List;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;

/**
 *
 * @author VU VIET PHUONG
 */
public interface SupplierServiceDelegate {

    public List<SupplierVO> getSupplierList();

    public void addSupplier(SupplierVO supplierVO)
            throws InvalidInputException, DuplicateException;

    public void updateSupplier(SupplierVO supplierVO)
            throws InvalidInputException, DuplicateException;

    public void deleteSupplier(List<Long> movCatIdList, boolean ignoreReference)
            throws DBReferenceViolationException;

}
