/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.event;

import java.util.EventObject;
import java.util.List;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class SupplierChangeEvent extends EventObject {
    private List<SupplierVO> supplierList;

    public SupplierChangeEvent(Object source) {
        super(source);
    }

    public List<SupplierVO> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<SupplierVO> supplierList) {
        this.supplierList = supplierList;
    }
    
}
