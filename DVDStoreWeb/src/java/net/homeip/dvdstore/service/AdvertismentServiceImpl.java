/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service;

import java.util.ArrayList;
import java.util.List;
import net.homeip.dvdstore.dao.AdvertismentDAO;
import net.homeip.dvdstore.dao.ConfigurationDAO;
import net.homeip.dvdstore.pojo.Advertisment;
import net.homeip.dvdstore.pojo.web.vo.AdvertismentVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class AdvertismentServiceImpl implements AdvertismentService {
    private AdvertismentDAO advertismentDAO;
    
    @Override
    public List<AdvertismentVO> getAdvertismentList() {        
        List<Advertisment> advertismentList = advertismentDAO.findAdvertisment();
        List<AdvertismentVO> voList = new ArrayList<AdvertismentVO>(advertismentList.size());
        AdvertismentVO vo;
        for (Advertisment ad : advertismentList) {
            vo = new AdvertismentVO();
            vo.setAdLink(ad.getAdLink());
            vo.setAdSource(ConfigurationDAO.APSOLUTE_UPLOAD_DIR + ad.getAdPicName());
            voList.add(vo);
        }
        return voList;
    }

    public void setAdvertismentDAO(AdvertismentDAO advertismentDAO) {
        this.advertismentDAO = advertismentDAO;
    }

}
