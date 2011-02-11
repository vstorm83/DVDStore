/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import java.util.List;
import javax.xml.ws.WebServiceException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.pojo.webservice.vo.ChooseMovieCatgoryVO;
import net.homeip.dvdstore.webservice.ChooseMovieDialogService;

/**
 *
 * @author VU VIET PHUONG
 */
public class ChooseMovieDialogServiceDelegateImpl implements ChooseMovieDialogServiceDelegate {
    private ChooseMovieDialogService chooseMovieDialogService;

    public List<ChooseMovieCatgoryVO> getChooseMovieCatgoryVOs() {
        if (chooseMovieDialogService == null) {
            throw new IllegalStateException("chooseMovieDialogService has not set");
        }

        try {
            return chooseMovieDialogService.getChooseMovieCatgoryVOs();
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void setChooseMovieDialogService(ChooseMovieDialogService chooseMovieDialogService) {
        this.chooseMovieDialogService = chooseMovieDialogService;
    }

}
