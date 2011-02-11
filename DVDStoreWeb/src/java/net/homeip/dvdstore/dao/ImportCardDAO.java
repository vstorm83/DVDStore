/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.GregorianCalendar;
import java.util.List;
import net.homeip.dvdstore.pojo.ImportCard;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ImportCardDAO {

    public List<ImportCard> findImportCardBySupplierId(Long supplierId);

    public List<ImportCard> findImportCardByMovieId(Long movId);

    public List<ImportCard> findImportCard(String supplierNameSearch,
            GregorianCalendar start, GregorianCalendar end);

    public ImportCard createImportCard();

    public void save(ImportCard importCard);

    public ImportCard getImportCardById(Long importCardId);

    public void deleteAll(List<ImportCard> importCards);

}
