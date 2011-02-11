/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.frame;

import java.util.EventObject;
import net.homeip.dvdstore.model.DataChangingModel;

/**
 *
 * @author VU VIET PHUONG
 */
public class UploadEvent extends EventObject {
    private byte[] fileUploaded;
    private String fileName;
    private DataChangingModel dataChangingModel;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileUploaded() {
        return fileUploaded;
    }

    public void setFileUploaded(byte[] fileUploaded) {
        this.fileUploaded = fileUploaded;
    }

    public DataChangingModel getDataChangingModel() {
        return dataChangingModel;
    }

    public void setDataChangingModel(DataChangingModel dataChangingModel) {
        this.dataChangingModel = dataChangingModel;
    }

    public UploadEvent(Object source) {
        super(source);
    }
    
}
