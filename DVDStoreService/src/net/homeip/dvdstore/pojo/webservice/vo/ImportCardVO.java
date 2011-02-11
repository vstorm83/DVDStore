/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author VU VIET PHUONG
 */
public class ImportCardVO {
    private Long importCardId;
    private SupplierVO supplierVO;
    private List<ImportCardItemVO> importCardItemVOs;
    private Date dateCreated;

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

    public List<ImportCardItemVO> getImportCardItemVOs() {
        return importCardItemVOs;
    }

    public void setImportCardItemVOs(List<ImportCardItemVO> importCardItemVOs) {
        this.importCardItemVOs = importCardItemVOs;
    }

    public SupplierVO getSupplierVO() {
        return supplierVO;
    }

    public void setSupplierVO(SupplierVO supplierVO) {
        this.supplierVO = supplierVO;
    }
    
}
