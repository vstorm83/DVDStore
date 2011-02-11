/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate.frame;

import java.util.List;
import javax.xml.ws.WebServiceException;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.pojo.webservice.vo.GoodsMovieVO;
import net.homeip.dvdstore.pojo.webservice.vo.InitGoodsIFrameVO;
import net.homeip.dvdstore.webservice.GoodsIFrameService;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class GoodsIFrameServiceDelegateImpl implements GoodsIFrameServiceDelegate {
    private GoodsIFrameService goodsIFrameService;

    public void upload(String fileName, byte[] fileUploaded) {
        try {
            goodsIFrameService.upload(fileName, fileUploaded);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void addMovie(GoodsMovieVO goodsMovieVO) throws InvalidInputException, DuplicateException {
        try {
            goodsIFrameService.addMovie(goodsMovieVO);
        } catch (InvalidAdminInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException(ex.getMessage(), ex);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void deleteMovie(List<Long> goodsMovieIdList, boolean ignoreReference)
            throws DBReferenceViolationException {
        try {
            goodsIFrameService.deleteMovie(goodsMovieIdList, ignoreReference);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void updateMovie(GoodsMovieVO goodsMovieVO)
            throws InvalidInputException, DuplicateException {
        try {
            goodsIFrameService.updateMovie(goodsMovieVO);
        } catch (InvalidAdminInputException ex) {
            throw new InvalidInputException(ex.getMessage(), ex);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public InitGoodsIFrameVO getInitGoodsIFrameVO(String searchByMovName,
            MovieCatgoryVO searchByMovCat) {
        if (goodsIFrameService == null) {
            throw new IllegalStateException("goodsIFrameService has not set");
        }

        try {
            return goodsIFrameService.getInitGoodsIFrameVO(searchByMovName,
                    searchByMovCat);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public List<GoodsMovieVO> getGoodsMovieVOs(String searchByMovName, MovieCatgoryVO searchByMovCat) {
        if (goodsIFrameService == null) {
            throw new IllegalStateException("goodsIFrameService has not set");
        }

        try {
            return goodsIFrameService.getGoodsMovieVOs(searchByMovName,
                    searchByMovCat);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void setGoodsIFrameService(GoodsIFrameService goodsIFrameService) {
        this.goodsIFrameService = goodsIFrameService;
    }

}
