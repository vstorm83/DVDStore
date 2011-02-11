/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.dao.ImportCardDAO;
import net.homeip.dvdstore.dao.SupplierDAO;
import net.homeip.dvdstore.pojo.Supplier;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import org.apache.log4j.Logger;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.SupplierService")
public class SupplierServiceImpl implements SupplierService {

    private SupplierDAO supplierDAO;
    private ImportCardDAO importCardDAO;
    private Logger logger = Logger.getLogger(SupplierServiceImpl.class);

    @Override
    public List<SupplierVO> getSupplierList() {
        List<Supplier> supplierList = supplierDAO.findSupplier();
        List<SupplierVO> voList = new ArrayList<SupplierVO>(supplierList.size());
        SupplierVO vo;
        for (Supplier supplier : supplierList) {
            vo = new SupplierVO();
            vo.setSupplierId(supplier.getSupplierId());
            vo.setSupplierName(supplier.getSupplierName());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public SupplierVO getSupplierById(long supplierId) {
        Supplier supplier = supplierDAO.getSupplierById(supplierId);
        if (supplier == null) {
            return null;
        }
        SupplierVO vo = new SupplierVO();
        vo.setSupplierId(supplier.getSupplierId());
        vo.setSupplierName(supplier.getSupplierName());
        return vo;
    }

    public void addSupplier(SupplierVO supplierVO)
            throws InvalidAdminInputException, DuplicateException {
        if (supplierVO == null) {
            throw new IllegalArgumentException("can't add null supplierVO");
        }
        logger.trace("addSupplier supplierName=" + supplierVO.getSupplierName());
        Supplier supplier = supplierDAO.createSupplier();
        supplier.populate(supplierVO, supplierDAO);
        supplierDAO.saveSupplier(supplier);
    }

    public void deleteSupplier(List<Long> supplierIdList, boolean ignoreReference)
            throws DBReferenceViolationException {
        if (supplierIdList == null) {
            throw new IllegalArgumentException("supplierIdList is null");
        }
        logger.trace("deleteSupplier supplierIdList.size()=" + supplierIdList.size());
        List<Supplier> supplierList = new ArrayList<Supplier>(supplierIdList.size());
        List<String> violationNameList = new ArrayList<String>();
        Supplier supplier;
        for (Long id : supplierIdList) {
            supplier = supplierDAO.getSupplierById(id);
            if (!ignoreReference) {
                if (importCardDAO.findImportCardBySupplierId(id).size() > 0) {
                    violationNameList.add(supplier.getSupplierName());
                }
            }
            supplierList.add(supplier);
        }
        if (violationNameList.size() > 0) {
            logger.trace("deleteSupplier violationNameList.size()=" + violationNameList.size());
            DBReferenceViolationException exp = new DBReferenceViolationException();
            exp.setViolationName(violationNameList);
            throw exp;
        } else {
            logger.trace("deleteSupplier supplierList.size()=" + supplierList.size());
            supplierDAO.deleteAll(supplierList);
        }
    }

    public void updateSupplier(SupplierVO supplierVO)
            throws InvalidAdminInputException, DuplicateException {
        if (supplierVO == null) {
            throw new IllegalArgumentException("can't update null supplierVO");
        }
        Supplier supplier = supplierDAO.getSupplierById(supplierVO.getSupplierId());
        if (supplier == null) {
            throw new IllegalStateException("can't update supplier has not found");
        }
        supplier.populate(supplierVO, supplierDAO);
        supplierDAO.saveSupplier(supplier);
    }

    public void setSupplierDAO(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    public void setImportCardDAO(ImportCardDAO importCardDAO) {
        this.importCardDAO = importCardDAO;
    }
}
