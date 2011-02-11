/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service;

import java.util.List;
import net.homeip.dvdstore.pojo.web.vo.AdvertismentVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface AdvertismentService {
    List<AdvertismentVO> getAdvertismentList();
}
