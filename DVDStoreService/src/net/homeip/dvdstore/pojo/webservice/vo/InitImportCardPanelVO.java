/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

import java.util.List;

/**
 *
 * @author VU VIET PHUONG
 */
public class InitImportCardPanelVO {
    private List<ImportCardVO> importCardVOs;
    private List<SupplierVO> supplierVOs;

    public List<ImportCardVO> getImportCardVOs() {
        return importCardVOs;
    }

    public void setImportCardVOs(List<ImportCardVO> importCardVOs) {
        this.importCardVOs = importCardVOs;
    }

    public List<SupplierVO> getSupplierVOs() {
        return supplierVOs;
    }

    public void setSupplierVOs(List<SupplierVO> supplierVOs) {
        this.supplierVOs = supplierVOs;
    }
    
}
