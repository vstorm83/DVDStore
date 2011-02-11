/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import net.homeip.dvdstore.dao.SupplierDAO;
import net.homeip.dvdstore.pojo.webservice.vo.ImportCardItemVO;
import net.homeip.dvdstore.pojo.webservice.vo.ImportCardVO;
import net.homeip.dvdstore.service.exception.InvalidInputException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class ImportCard {
    private Long importCardId;
    private Supplier supplier;
    private Set<ImportCardItem> importCardItem = new HashSet<ImportCardItem>();
    private Date dateCreated;
    private int version;

//    public void addImportCardItem(ImportCardItem item) {
//        importCardItem.add(item);
//    }

//    public void removeImportCardItem(ImportCardItem item) {
//        importCardItem.remove(item);
//    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getImportCardId() {
        return importCardId;
    }

    public void setImportCardId(Long importCardId) {
        this.importCardId = importCardId;
    }

    public Set<ImportCardItem> getImportCardItem() {
        return importCardItem;
    }

    public void setImportCardItem(Set<ImportCardItem> importCardItem) {
        this.importCardItem = importCardItem;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void populate(Supplier supplier, String timeZoneId) {
        this.supplier = supplier;
        this.dateCreated = new Date(new Date().getTime()
                - TimeZone.getTimeZone(timeZoneId).getRawOffset());
    }

    public void update(ImportCardVO importCardVO, SupplierDAO supplierDAO)
            throws InvalidAdminInputException {
        validate(importCardVO);
        this.supplier = supplierDAO.getSupplierById(importCardVO.getSupplierVO().getSupplierId());

        for (ImportCardItemVO importCardItemVO : importCardVO.getImportCardItemVOs()) {
            for (ImportCardItem itm : importCardItem) {
                if (itm.getMovie().getMovId().longValue() == importCardItemVO.getMovId().longValue()) {
                    itm.setMovPrice(importCardItemVO.getMovPrice());                    
                    itm.setQuantity(importCardItemVO.getQuantity());
                    break;
                }
            }
        }
    }

    private void validate(ImportCardVO importCardVO) {
        if (importCardVO.getSupplierVO() == null ||
                importCardVO.getSupplierVO().getSupplierId() == null) {
            throw new IllegalArgumentException("can't update importCard with null supplier");
        }
        List<ImportCardItemVO> importCardItemVOs = importCardVO.getImportCardItemVOs();
        for (ImportCardItemVO importCardItemVO : importCardItemVOs) {
            if (importCardItemVO.getMovPrice() < 0) {
                throw new InvalidInputException("movPrice");
            }
            if (importCardItemVO.getQuantity() <= 0) {
                throw new InvalidInputException("quantity");
            }
        }
    }
    
}
