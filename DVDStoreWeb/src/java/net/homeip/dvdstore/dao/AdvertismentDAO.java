/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.List;
import net.homeip.dvdstore.pojo.Advertisment;

/**
 *
 * @author VU VIET PHUONG
 */
public interface AdvertismentDAO {

    public List<Advertisment> findAdvertisment();
    
}
